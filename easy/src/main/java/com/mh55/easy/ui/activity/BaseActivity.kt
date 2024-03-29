package com.mh55.easy.ui.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.mh55.easy.ext.bindingInflate
import com.mh55.easy.mvvm.BaseViewModel

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes
    private val layoutId: Int,
    private val varViewModelId: Int?=null,
) : AbsActivity<V, VM>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): V =
        bindingInflate(inflater, layoutId, container)


    @SuppressLint("MissingSuperCall")
    final override fun initViewAndViewModel() {
        super.initViewAndViewModel()
//        // 绑定 v 和 vm
        if (varViewModelId != null) {
            mBinding.setVariable(varViewModelId, mViewModel)
        }
        // 让 LiveData 和 xml 可以双向绑定
        mBinding.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}