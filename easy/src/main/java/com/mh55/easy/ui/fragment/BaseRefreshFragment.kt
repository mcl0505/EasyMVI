package com.mh55.easy.ui.fragment

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh55.easy.manager.AppManager
import com.mh55.easy.mvvm.BaseViewModel
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.ui.recycler.BaseRefreshProvider
import com.mh55.easy.ui.recycler.BindAdapter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

abstract class BaseRefreshFragment<VDB : ViewDataBinding, BRVM : BaseViewModel,VIDB: ViewDataBinding,T>(
    val layoutRes: Int,
    val itemLayoutRes: Int,
    val viewModelId: Int = 0
) : BaseFragment<VDB, BRVM>(layoutRes, viewModelId), BaseRefreshProvider<T, VIDB> {

    /*** 每页请求数量   */
    open var mLimit = 20
    /*** 当前请求的页数   */
    open var mPage = 1
    lateinit var mAdapter: BindAdapter<T, VIDB>

    open fun isResumeRefresh(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        if (isResumeRefresh()){
            mPage = 1
            onRefreshData()
        }
    }

    override fun initData() {
        super.initData()
        mAdapter = object : BindAdapter<T, VIDB>(itemLayoutRes){
            override fun convertBind(holder: BaseViewHolder, item: T, binding: VIDB) {
                convertData(holder,item,binding,getItemPosition(item))
            }

            override fun convertBind(
                holder: BaseViewHolder,
                item: T,
                binding: VIDB,
                payloads: List<Any>
            ) {
                convertDataPayloads(holder,item,binding,getItemPosition(item),payloads)
            }

        }

        getRecyclerView().apply {
            layoutManager = getDefaultLayoutManager()
            otherRecyclerViewSetting()
            adapter = mAdapter
        }

        getSmartRefreshLayout().apply {
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                /**
                 * 刷线
                 */
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mPage = 1
                    onRefreshData()
                }

                /**
                 * 加载  页码自动增加
                 */
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    mPage += 1
                    onRefreshData()
                }

            })
            setEnableLoadMore(false)
        }

        if (!isResumeRefresh()){
            mPage = 1
            onRefreshData()
        }
    }

    open fun otherRecyclerViewSetting() {}

    open fun getDefaultLayoutManager(): RecyclerView.LayoutManager? {
        return LinearLayoutManager(AppManager.getContext())
    }

    override fun convertDataPayloads(
        baseViewHolder: BaseViewHolder,
        item: T,
        binding: VIDB,
        position: Int,
        payloads: List<Any>
    ) {

    }
}