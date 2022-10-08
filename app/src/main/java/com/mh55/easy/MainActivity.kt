package com.mh55.easy

import com.mh55.easy.databinding.ActivityMainBinding
import com.mh55.easy.view_model.MainViewModel
import com.mh55.easymvvm.ext.toast
import com.mh55.easymvvm.ui.activity.AbsActivity
import com.mh55.easymvvm.ui.activity.BaseActivity
import com.mh55.easymvvm.utils.LogUtil

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main,BR.viewModel) {

    override fun setTitleText(): String = "MainActivity"

    override fun initData() {
        super.initData()
        mBinding.click = this
        LogUtil.d("测试打印11111111")
    }

    fun showButtom() = "MainActivity".toast()




}