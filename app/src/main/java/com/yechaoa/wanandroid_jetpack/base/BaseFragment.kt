package com.yechaoa.wanandroid_jetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by yechaoa on 2020/2/4.
 * Describe :
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected open var binding: VB? = null
    protected open val mBinding get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding()
        return mBinding.root
    }

    abstract fun getViewBinding(): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    open fun initialize() {}

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}