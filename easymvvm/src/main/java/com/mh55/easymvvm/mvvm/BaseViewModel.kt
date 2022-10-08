package com.mh55.easymvvm.mvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.imyyq.mvvm.base.IActivityResult
import com.imyyq.mvvm.base.IArgumentsFromIntent
import com.mh55.easymvvm.mvvm.intent.BaseViewIntent
import com.mh55.easymvvm.mvvm.live_data.BooleanLiveData
import com.mh55.easymvvm.mvvm.live_data.EventLiveData
import com.mh55.easymvvm.mvvm.live_data.SingleLiveEvent
import com.mh55.easymvvm.mvvm.live_data.StringLiveData
import com.mh55.easymvvm.ui.IArgumentsFromBundle
import com.mh55.easymvvm.utils.bus.LiveDataBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel(), DefaultLifecycleObserver, IActivityResult, IArgumentsFromBundle,
    IArgumentsFromIntent {

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

}