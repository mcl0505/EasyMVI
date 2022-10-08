package com.mh55.easymvvm.ext

import android.graphics.drawable.Drawable
import com.mh55.easymvvm.utils.ToastUtil

fun Int.toast(img:Drawable?=null){
    ToastUtil.toast(this.getString(),img)
}

fun String.toast(img:Drawable?=null){
    ToastUtil.toast(this,img)
}