package com.yechaoa.wanandroid_jetpack.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yechaoa.wanandroid_jetpack.data.http.ApiException
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.yechaoa.yutilskt.LogUtil
import com.yechaoa.yutilskt.ToastUtil
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by yechaoa on 2020/2/4.
 * Describe :
 */
typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

open class BaseViewModel : ViewModel() {

    val needLogin = MutableLiveData<Boolean>().apply { value = false }

    /**
     * 创建并执行协程 Coroutines
     * @param block 协程中执行
     * @param error 错误时执行
     * @param cancel 取消时执行
     * @param showErrorToast 是否弹出错误吐司
     * @return Job API 文档 https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
     *
     * CoroutineScope.launch 函数返回的是一个 Job 对象，代表一个异步的任务。
     * viewModelScope 也是继承 CoroutineScope的
     * Job 具有生命周期并且可以取消。
     * Job 还可以有层级关系，一个Job可以包含多个子Job，当父Job被取消后，所有的子Job也会被自动取消；
     * 当子Job被取消或者出现异常后父Job也会被取消。
     */
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T> 继承自 Job 额外多3个方法
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke(this) }
    }

    /**
     * 取消协程 会抛出CancellationException
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    403 -> {
                        if (showErrorToast) ToastUtil.show(e.message)
                        needLogin.value = true
                    }
                    400 -> {
                        if (showErrorToast) ToastUtil.show(e.message)
                    }
                    -1 -> {
                        if (showErrorToast) ToastUtil.show(e.message)
                    }
                    -50000 -> {
                        if (showErrorToast) ToastUtil.show("-50000" + e.message)
                    }
                    5039 -> {
                        if (showErrorToast) ToastUtil.show(e.message)
                    }
                    -1794 -> {
                        if (showErrorToast) ToastUtil.show("操作过于频繁，请1分钟后再试")
                    }
                    // 其他错误
                    else -> {
                        if (showErrorToast) ToastUtil.show(e.message)
                    }
                }
                LogUtil.e(e.message)
            }
            // 网络请求失败
            is ConnectException, is SocketTimeoutException, is UnknownHostException, is HttpException -> {
                if (showErrorToast) ToastUtil.show("网络请求失败")
                LogUtil.e("网络请求失败" + e.message)
            }
            // 数据解析错误
            is JsonParseException -> {
                if (showErrorToast) ToastUtil.show("数据解析错误")
                LogUtil.e("数据解析错误" + e.message)
            }
            // 其他错误
            else -> {
                if (showErrorToast) ToastUtil.show(e.message ?: return)
                LogUtil.e(e.message ?: return)
            }

        }
    }

    /**
     * json提交 转RequestBody （表单提交 @FieldMap）
     */
    protected fun toRequestBody(params: Any?): RequestBody {
        return Gson().toJson(params).toRequestBody("application/json".toMediaTypeOrNull())
    }
}