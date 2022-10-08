package com.mh55.easy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mh55.easy.databinding.ActivityUserBinding
import com.mh55.easy.view_model.MainViewModel
import com.mh55.easymvvm.ui.activity.BaseActivity

class UserActivity : BaseActivity<ActivityUserBinding,MainViewModel>(R.layout.activity_user,BR.viewModel) {
    override fun initData() {
        super.initData()
        setResult(RESULT_OK, mutableMapOf("title" to "用户信息"),bundle = null,data = null)
    }
}