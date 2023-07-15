package com.yechaoa.wanandroid_jetpack.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.yutilskt.DisplayUtil

/**
 * Created by yechaoa on 2020/2/4.
 * Describe :
 */
abstract class BaseBottomSheetDialog<VB : ViewBinding> : BottomSheetDialogFragment() {

    protected open lateinit var mBinding: VB

    private var isFull = false

    /**
     * setStyle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        mBinding = getViewBinding()
        dialog.setContentView(mBinding.root)
        isNeedFull()
        initView(mBinding.root)
        return dialog
    }

    abstract fun getViewBinding(): VB

    /**
     * 初始化view相关
     */
    abstract fun initView(view: View)

    /**
     * 是否需要全屏显示 默认不全屏
     */
    open fun isNeedFull(isNeed: Boolean = false) {
        isFull = isNeed
    }

    /**
     * 设置固定高度
     */
    override fun onStart() {
        super.onStart()
        if (isFull) {
            //拿到系统的 bottom_sheet，然后把view的高度设置给bottom_sheet
            val view: FrameLayout = dialog?.window?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from(view)
            //设置弹出高度
            behavior.peekHeight = DisplayUtil.getScreenHeight()
            //设置展开状态
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        hideKeyBoard()
        super.onCancel(dialog)
    }

    override fun dismiss() {
        hideKeyBoard()
        super.dismiss()
    }

    /**
     * dialog中 关闭软键盘
     */
    private fun hideKeyBoard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = dialog?.window?.currentFocus
        view?.let {
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}