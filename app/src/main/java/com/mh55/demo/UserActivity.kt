package com.mh55.demo

import com.mh55.demo.databinding.ActivityUserBinding
import com.mh55.demo.view_model.MainViewModel
import com.mh55.easy.ui.activity.BaseActivity

class UserActivity : BaseActivity<ActivityUserBinding, MainViewModel>(
    R.layout.activity_user,
    BR.viewModel
) {
    override fun initData() {
        super.initData()
        setResult(RESULT_OK, mutableMapOf("title" to "用户信息"),bundle = null,data = null)
    }
}