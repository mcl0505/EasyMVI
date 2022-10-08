package com.mh55.easymvvm.mvvm

/**
 * 公司名称：~漫漫人生路~总得错几步~
 * 创建作者：Android 孟从伦
 * 创建时间：2022/10/3
 * 功能描述：网络请求开始时， 是否显示 Loading 和 Dialog
 */

data class LoadingEntity(
    val isLoading: Boolean = false,
    val isDialog: Boolean = true,
    val dialogContent: String = ""
)