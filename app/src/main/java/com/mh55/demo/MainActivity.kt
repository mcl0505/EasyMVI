package com.mh55.demo

import com.mh55.demo.databinding.ActivityMainBinding
import com.mh55.demo.fragment.IndexFragment
import com.mh55.demo.fragment.MineFragment
import com.mh55.demo.view_model.MainViewModel
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.widget.tab.withViewPager
import com.mh55.demo.BR

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
    BR.viewModel
) {

    override fun initData() {
        super.initData()
        mBinding.mTabButtonGroup.withViewPager(this,mBinding.mViewPager, mutableListOf(
            IndexFragment.getInstance(mutableMapOf("text" to "123")),
            MineFragment.getInstance()
        ))
    }




}