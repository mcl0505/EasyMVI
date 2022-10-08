package com.mh55.easymvvm.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.kckj.baselibrary.ext.bindingInflate
import com.mh55.easymvvm.mvvm.BaseViewModel

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int,
    private val varViewModelId: Int? = null,
    sharedViewModel: Boolean = false
) : AbsFragment<V, VM>(sharedViewModel) {

    final override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): V = bindingInflate(inflater,layoutId,container)

    final override fun initViewAndViewModel() {
        super.initViewAndViewModel()
        // 绑定 v 和 vm
        if (varViewModelId != null) {
            mBinding.setVariable(varViewModelId, mViewModel)
        }

        // 让 LiveData 和 xml 可以双向绑定
        mBinding.lifecycleOwner = this
    }

    override fun onDestroyView() {
        mBinding.unbind()
        super.onDestroyView()
    }
}