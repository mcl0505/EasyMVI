package com.mh55.easymvvm.ext

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.ethanhua.skeleton.ViewSkeletonScreen
import com.mh55.easymvvm.R

/**
 *   公司名称: ~漫漫人生路~总得错几步~
 *   创建作者: Android 孟从伦
 *   创建时间: 2022/10/09
 *   功能描述: 骨架布局扩展
 */

fun View.bindSkeleton(layoutId:Int): ViewSkeletonScreen.Builder = Skeleton.bind(this)
    .load(layoutId)//显示的布局
    .shimmer(true)//是否显示光晕动画
    .color(R.color.app_system_color)
    .duration(1*1000)//光晕动画显示一圈的时间

fun RecyclerView.bindSkeletonRecycler(layoutId:Int,mAdapter:RecyclerView.Adapter<BaseViewHolder>): RecyclerViewSkeletonScreen.Builder = Skeleton.bind(this)
    .adapter(mAdapter)
    .load(layoutId)//显示的布局
    .shimmer(true)//是否显示光晕动画
    .color(R.color.app_system_color)
    .duration(1*1000)//光晕动画显示一圈的时间