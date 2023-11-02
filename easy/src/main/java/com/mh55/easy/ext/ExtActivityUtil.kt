package com.mh55.easy.ext

import android.net.Network
import androidx.databinding.ViewDataBinding
import com.mh55.easy.R
import com.mh55.easy.mvvm.BaseViewModel
import com.mh55.easy.ui.activity.BaseRefreshActivity
import com.mh55.easy.ui.fragment.BaseRefreshFragment
import com.mh55.easy.utils.EmptyViewUtil

fun <VDB : ViewDataBinding, BRVM : BaseViewModel,VIDB: ViewDataBinding,T> BaseRefreshActivity<VDB, BRVM, VIDB, T>.loadPage(
    mList: MutableList<T> = ArrayList(), mDrawable: Int = R.drawable.empty_pic_data,
    desc: String = ""
){
    //加载全部数据  不需要分页
    if (mLimit<=0){
        mAdapter.setNewInstance(mList)
        getSmartRefreshLayout().apply {
            finishRefresh()
            setEnableLoadMore(false)
        }

        if (mAdapter.itemCount == 0) {
            if (!isConnected()) {
                mAdapter.setEmptyView(R.layout.view_net_error)
            } else {
                mAdapter.setEmptyView(R.layout.view_no_data)
                mAdapter.setEmptyView(EmptyViewUtil.setEmptyView(this, getRecyclerView(), mDrawable,desc))
            }
        }

        return
    }


    if (mPage == 1) {
        mAdapter.setNewInstance(mList)
    } else {
        mAdapter.addData(mList)
    }
    if (mAdapter.itemCount == 0) {
        if (!isConnected()) {
            mAdapter.setEmptyView(R.layout.view_net_error)
        } else {
            mAdapter.setEmptyView(R.layout.view_no_data)
            mAdapter.setEmptyView(EmptyViewUtil.setEmptyView(this, getRecyclerView(), mDrawable,desc))
        }
    }
    if (mAdapter.itemCount < mLimit * mPage) {
        getSmartRefreshLayout().apply {
            finishLoadMoreWithNoMoreData()
            setEnableLoadMore(false)
        }
    }else {
        getSmartRefreshLayout().apply {
            setEnableLoadMore(true)
        }
    }
    getSmartRefreshLayout().apply {
        finishLoadMore()
        finishRefresh()
    }
}

fun <VDB : ViewDataBinding, BRVM : BaseViewModel,VIDB: ViewDataBinding,T> BaseRefreshFragment<VDB, BRVM, VIDB, T>.loadPage(
    mList: MutableList<T> = ArrayList(), mDrawable: Int = R.drawable.empty_pic_data,
    desc: String = ""
){
    //加载全部数据  不需要分页
    if (mLimit<=0){
        mAdapter.setNewInstance(mList)
        getSmartRefreshLayout().apply {
            finishRefresh()
            setEnableLoadMore(false)
        }

        if (mAdapter.itemCount == 0) {
            if (!isConnected()) {
                mAdapter.setEmptyView(R.layout.view_net_error)
            } else {
                mAdapter.setEmptyView(R.layout.view_no_data)
                mAdapter.setEmptyView(EmptyViewUtil.setEmptyView(this.requireContext(), getRecyclerView(), mDrawable,desc))
            }
        }

        return
    }


    if (mPage == 1) {
        mAdapter.setNewInstance(mList)
    } else {
        mAdapter.addData(mList)
    }
    if (mAdapter.itemCount == 0) {
        if (!isConnected()) {
            mAdapter.setEmptyView(R.layout.view_net_error)
        } else {
            mAdapter.setEmptyView(R.layout.view_no_data)
            mAdapter.setEmptyView(EmptyViewUtil.setEmptyView(this.requireContext(), getRecyclerView(), mDrawable,desc))
        }
    }
    if (mAdapter.itemCount < mLimit * mPage) {
        getSmartRefreshLayout().apply {
            finishLoadMoreWithNoMoreData()
            setEnableLoadMore(false)
        }
    }else {
        getSmartRefreshLayout().apply {
            setEnableLoadMore(true)
        }
    }
    getSmartRefreshLayout().apply {
        finishLoadMore()
        finishRefresh()
    }
}