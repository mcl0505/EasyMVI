package com.mh55.easymvvm.ext

import android.os.Looper

/**
 * 判断是否处于UI线程
 */
fun isInUIThread() = Looper.getMainLooper().thread == Thread.currentThread()