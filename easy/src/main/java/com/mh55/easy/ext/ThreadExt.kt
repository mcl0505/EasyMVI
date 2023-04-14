package com.mh55.easy.ext

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 判断是否处于UI线程
 */
fun isInUIThread() = Looper.getMainLooper().thread == Thread.currentThread()

private val mHandler = Handler(Looper.getMainLooper())

private val mSingleService: ExecutorService = Executors.newSingleThreadExecutor()

/**
 * 切换到主线程
 */
fun <T> T.ktxRunOnUiThread(block: T.() -> Unit) {
    mHandler.post {
        block()
    }
}

/**
 * 延迟delayMills切换到主线程,默认是600ms
 */
fun <T> T.ktxRunOnUiThreadDelay(delayMills: Long=600, block: T.() -> Unit) {
    mHandler.postDelayed({
        block()
    }, delayMills)
}

/**
 * 子线程执行
 */
fun <T> T.ktxRunOnThreadSingle(block: T.() -> Unit) {
    mSingleService.execute {
        block()
    }
    

}

