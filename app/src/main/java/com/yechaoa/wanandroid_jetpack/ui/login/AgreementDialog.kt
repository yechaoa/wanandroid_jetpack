package com.yechaoa.wanandroid_jetpack.ui.login

import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.content.ContextCompat
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.wanandroid_jetpack.base.BaseBottomSheetDialog
import com.yechaoa.wanandroid_jetpack.databinding.DialogAgreementBinding
import com.yechaoa.wanandroid_jetpack.util.setOnclickNoRepeat

/**
 * GitHub : https://github.com/yechaoa
 * CSDN : http://blog.csdn.net/yechaoa
 *
 * Created by yechao on 2023/7/15.
 * Describe :
 */
class AgreementDialog(private var tip: SpannableStringBuilder, private val clickListener: (String) -> Unit) :
    BaseBottomSheetDialog<DialogAgreementBinding>() {

    companion object {
        const val AGREE = "AGREE"
        const val NOT_AGREE = "NOT_AGREE"
    }

    override fun getViewBinding(): DialogAgreementBinding {
        return DialogAgreementBinding.inflate(this.layoutInflater)
    }

    override fun initView(view: View) {

        mBinding.tvAgreementTip.movementMethod = LinkMovementMethod.getInstance()
        mBinding.tvAgreementTip.text = tip
        mBinding.tvAgreementTip.highlightColor = ContextCompat.getColor(requireContext(), R.color.transparent)

        mBinding.btnAgree.setOnclickNoRepeat {
            clickListener.invoke(AGREE)
            dismiss()
        }

        mBinding.btnNotAgree.setOnclickNoRepeat {
            clickListener.invoke(NOT_AGREE)
            dismiss()
        }

    }

}