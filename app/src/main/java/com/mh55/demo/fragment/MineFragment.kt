package com.mh55.demo.fragment

import com.mh55.demo.databinding.FragmentMineBinding
import com.mh55.demo.BR
import com.mh55.demo.R
import com.mh55.demo.view_model.MainViewModel
import com.mh55.easy.ext.mapToBundle
import com.mh55.easy.ui.fragment.BaseFragment

class MineFragment : BaseFragment<FragmentMineBinding, MainViewModel>(R.layout.fragment_mine, BR.viewModel) {

    companion object {
        fun getInstance(map: MutableMap<String, Any?>? = null) = MineFragment().also {
            it.arguments = map.mapToBundle()
        }
    }

    override fun initData() {
        super.initData()


    }

}