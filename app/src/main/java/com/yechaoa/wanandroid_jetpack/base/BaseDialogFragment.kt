package com.yechaoa.wanandroid_jetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.yechaoa.yutilskt.DisplayUtil

/**
 * Created by yechao on 2020/2/4.
 * Describe :
 */
abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    protected open var binding: VB? = null
    protected open val mBinding get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding()
        return mBinding.root
    }

    abstract fun getViewBinding(): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    abstract fun initView(view: View)

    override fun onStart() {
        super.onStart()
        //设置宽高
        dialog!!.window!!.setLayout((DisplayUtil.getScreenWidth() * 0.88).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}