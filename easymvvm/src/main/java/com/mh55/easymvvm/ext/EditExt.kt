package com.mh55.easymvvm.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * 公司：~漫漫人生路~总得错几步~
 * 作者：Android 孟从伦
 * 创建时间：2021/3/22
 * 功能描述：输入框赋值
 */

fun EditText.setEditContent(text:String){
    this.text = Editable.Factory.getInstance().newEditable(text)
}
