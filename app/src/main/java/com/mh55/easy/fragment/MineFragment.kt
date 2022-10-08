package com.mh55.easy.fragment

import android.view.View
import com.mh55.easy.BR
import com.mh55.easy.R
import com.mh55.easy.databinding.FragmentMineBinding
import com.mh55.easy.dialog.CommonDialog
import com.mh55.easy.view_model.MainViewModel
import com.mh55.easymvvm.ext.mapToBundle
import com.mh55.easymvvm.ext.singleClick
import com.mh55.easymvvm.ui.fragment.BaseFragment

class MineFragment : BaseFragment<FragmentMineBinding, MainViewModel>(R.layout.fragment_mine, BR.viewModel) {

    companion object {
        fun getInstance(map: MutableMap<String,Any?>? = null) = MineFragment().also {
            it.arguments = map.mapToBundle()
        }
    }

    override fun initData() {
        super.initData()
        mBinding.apply {
            btn.singleClick {
                showLoading()
            }
        }
    }

}