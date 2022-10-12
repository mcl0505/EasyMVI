package com.mh55.easy

import com.mh55.easy.databinding.ActivityMainBinding
import com.mh55.easy.fragment.IndexFragment
import com.mh55.easy.fragment.MineFragment
import com.mh55.easy.view_model.MainViewModel
import com.mh55.easymvvm.App.AppUtil
import com.mh55.easymvvm.App.ConfigBuilder
import com.mh55.easymvvm.ext.toast
import com.mh55.easymvvm.ui.activity.AbsActivity
import com.mh55.easymvvm.ui.activity.BaseActivity
import com.mh55.easymvvm.utils.LogUtil
import com.mh55.easymvvm.widget.tab.withViewPager

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main,BR.viewModel) {

    override fun initData() {
        super.initData()
        mBinding.mTabButtonGroup.withViewPager(this,mBinding.mViewPager, mutableListOf(
            IndexFragment.getInstance(mutableMapOf("text" to "123")),
            MineFragment.getInstance()
        ))


        LogUtil.d(ConfigBuilder.Path.CRASH_LOG_PATH)
    }




}