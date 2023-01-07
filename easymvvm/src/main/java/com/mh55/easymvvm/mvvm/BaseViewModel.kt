package com.mh55.easymvvm.mvvm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.imyyq.mvvm.base.IActivityResult
import com.imyyq.mvvm.base.IArgumentsFromIntent
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.mh55.easymvvm.R
import com.mh55.easymvvm.ext.getString
import com.mh55.easymvvm.mvvm.intent.BaseViewIntent
import com.mh55.easymvvm.mvvm.live_data.BooleanLiveData
import com.mh55.easymvvm.mvvm.live_data.EventLiveData
import com.mh55.easymvvm.mvvm.live_data.SingleLiveEvent
import com.mh55.easymvvm.mvvm.live_data.StringLiveData
import com.mh55.easymvvm.ui.IArgumentsFromBundle
import com.mh55.easymvvm.ui.ILoading
import com.mh55.easymvvm.ui.loadsir.ILoadsir
import com.mh55.easymvvm.utils.bus.LiveDataBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel(), DefaultLifecycleObserver, IActivityResult, IArgumentsFromBundle,
    IArgumentsFromIntent,ILoadsir, ILoading {

    sealed class HttpState {
        data class HttpBefore(val isLoading: Boolean = false,
                              val isDialog: Boolean = true,
                              val dialogContent: String = ""):HttpState()

        data class HttpFailed(val code:Int,val msg:String = ""):HttpState()

        object HttpAfter:HttpState()
    }
    val httpState = SingleLiveEvent<HttpState>()

    // TODO: 2022/5/31 界面跳转 =======================================================================================================
    internal var mBundle: Bundle? = null
    internal var mIntent: Intent? = null
    override fun getBundle(): Bundle? = mBundle
    override fun getArgumentsIntent(): Intent? = mIntent

    val mUiChangeLiveData = SingleLiveEvent<BaseViewIntent>()
    override fun startActivity(clazz: Class<out Activity>, map: MutableMap<String, *>?, bundle: Bundle?) {
        mUiChangeLiveData.postValue(BaseViewIntent.startActivity(clazz, map, bundle))
    }
    override fun startActivityForResult(clazz: Class<out Activity>, map: MutableMap<String, *>?, bundle: Bundle?) {
        mUiChangeLiveData.postValue(BaseViewIntent.startActivityForResult(clazz, map, bundle))
    }
    override fun setResult(resultCode: Int, map: MutableMap<String, *>?, bundle: Bundle?, data: Intent?) {
        mUiChangeLiveData.postValue(BaseViewIntent.setResult(resultCode, map, bundle, data))
    }
    override fun finish(resultCode: Int, map: MutableMap<String, *>?, bundle: Bundle?) {
        mUiChangeLiveData.postValue(BaseViewIntent.finish(resultCode, map, bundle))
    }

    override fun onCleared() {
        super.onCleared()
        httpState.call()
        mUiChangeLiveData.call()
    }

    override fun showCallback(clazz: Class<out Callback>) {
        showCallback(clazz) { _, _ -> }
    }

    override fun showCallback(
        clazz: Class<out Callback>,
        block: (context: Context, view: View) -> Unit
    ) {
        mUiChangeLiveData.postValue(BaseViewIntent.showCallback(callback = clazz,block))
    }

    override fun dismissCallback() {
        mUiChangeLiveData.postValue(BaseViewIntent.showCallback(callback = SuccessCallback::class.java) { _, _ -> })
    }

    override fun showLoading() {
        showLoading(R.string.loading_msg.getString())
    }

    override fun showLoading(msg: String) {
        mUiChangeLiveData.postValue(BaseViewIntent.showLoading(true,msg))
    }

    override fun dismissLoading() {
        mUiChangeLiveData.postValue(BaseViewIntent.showLoading(false,R.string.loading_msg.getString()))
    }

}