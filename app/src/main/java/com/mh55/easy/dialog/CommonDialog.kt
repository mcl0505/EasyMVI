package com.mh55.easy.dialog

import android.view.Gravity
import com.mh55.easy.R
import com.mh55.easy.databinding.DailogCommonBinding
import com.mh55.easy.view_model.MainViewModel
import com.mh55.easymvvm.ui.dialog.BaseDialog

/**
 *   公司名称: ~漫漫人生路~总得错几步~
 *   创建作者: Android 孟从伦
 *   创建时间: 2022/10/08
 *   功能描述:
 */
class CommonDialog : BaseDialog<DailogCommonBinding,MainViewModel>(R.layout.dailog_common) {
    override fun setGravity(): Int {
        return Gravity.CENTER
    }
}