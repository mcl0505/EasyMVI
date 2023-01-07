package com.mh55.easymvvm.ext

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.mh55.easymvvm.EasyApplication

//获取颜色
fun Int.getColor() : Int = ContextCompat.getColor(EasyApplication.getContext(),this)

fun Int.getDrawable() : Drawable? = ContextCompat.getDrawable(EasyApplication.getContext(),this)

fun Int.getString() : String = EasyApplication.getContext().resources.getString(this)

fun Int.getStringArray() : List<String> = EasyApplication.getContext().resources.getStringArray(this).toList() as MutableList<String>