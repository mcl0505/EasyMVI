package com.mh55.easy.fragment

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.mh55.easy.BR
import com.mh55.easy.R
import com.mh55.easy.databinding.FragmentIndexBinding
import com.mh55.easy.view_model.MainViewModel
import com.mh55.easymvvm.ext.mapToBundle
import com.mh55.easymvvm.ui.fragment.BaseFragment

class IndexFragment : BaseFragment<FragmentIndexBinding, MainViewModel>(R.layout.fragment_index, BR.viewModel) {
    companion object {
        fun getInstance(map: MutableMap<String, Any?>? = null) = IndexFragment().also {
            it.arguments = map.mapToBundle()
        }
    }
}