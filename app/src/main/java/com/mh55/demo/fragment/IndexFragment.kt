package com.mh55.demo.fragment

import com.mh55.demo.databinding.FragmentIndexBinding
import com.mh55.demo.BR
import com.mh55.demo.R
import com.mh55.demo.view_model.MainViewModel
import com.mh55.easy.ext.mapToBundle
import com.mh55.easy.ui.fragment.BaseFragment

class IndexFragment : BaseFragment<FragmentIndexBinding, MainViewModel>(R.layout.fragment_index, BR.viewModel) {
    companion object {
        fun getInstance(map: MutableMap<String, Any?>? = null) = IndexFragment().also {
            it.arguments = map.mapToBundle()
        }
    }
}