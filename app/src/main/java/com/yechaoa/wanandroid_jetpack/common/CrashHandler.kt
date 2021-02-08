package com.yechaoa.wanandroid_jetpack.common

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.os.Process.killProcess
import android.os.Process.myPid
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yechaoa on 2020/2/4.
 * Describe : 异常日志
 */
class CrashHandler : Thread.UncaughtExceptionHandler {

    companion object {
        val instance by lazy { CrashHandler() }
    }

    private lateinit var mContext: Context

    private lateinit var mDefaultHandler: Thread.UncaughtExceptionHandler

    //用来存储设备信息和异常信息
    private var infos: HashMap<String, String> = HashMap()

    /**
     * application中初始化
     */
    fun init(context: Context) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置全局默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, e: Throwable) {
        if (!handleException(e) && mDefaultHandler != null) {
            // 没有处理还交给系统默认的处理器
            mDefaultHandler.uncaughtException(thread, e);
        } else {
            // 已经处理，结束进程
            killProcess(myPid())
            System.exit(1)
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * @param throwable 异常
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(throwable: Throwable?): Boolean {
        if (throwable == null) {
            return false
        }
        //使用Toast来显示异常信息
        object : Thread() {
            override fun run() {
                Looper.prepare()
                throwable.printStackTrace()
                Looper.loop()
            }
        }.start()

        //收集设备信息、版本信息、异常信息
        val info = getInfos(mContext, throwable)

        //保存日志文件
        saveCrashInfo2File(info)

        return true
    }

    /**
     * 收集设备信息、版本信息、异常信息
     */
    private fun getInfos(context: Context, throwable: Throwable): String {
        //版本信息
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        //设备信息
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field[null].toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //异常信息
        val sb = StringBuilder()
        for ((key, value) in infos.entries) {
            sb.append(key).append("=").append(value).append("\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        throwable.printStackTrace(printWriter)
        var cause = throwable.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        return sb.toString()
    }

    /**
     * 保存错误信息到文件中
     * @param crashInfo 这个 crashInfo 就是我们收集到的所有信息，可以做一个异常上报的接口用来提交用户的crash信息
     */
    private fun saveCrashInfo2File(crashInfo: String) {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateName = format.format(System.currentTimeMillis())
        val fileName: String = getFileDir() + File.separator + dateName + ".txt"
        val file = File(fileName)
        try {
            val fileWriter = FileWriter(file)
            fileWriter.write(crashInfo)
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 私有目录 适配android 10
     */
    private fun getFileDir(): String {
        // \内部存储\Android\data\com.yechaoa.wanandroid_jetpack\files\Documents\crash
        return mkdirs(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path + File.separator + "crash")
    }

    private fun mkdirs(dir: String): String {
        val file = File(dir)
        if (!file.exists()) {
            file.mkdirs()
        }
        return dir
    }
}