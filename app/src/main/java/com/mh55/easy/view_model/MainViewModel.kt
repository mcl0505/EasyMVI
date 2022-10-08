package com.mh55.easy.view_model

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.mh55.easy.UserActivity
import com.mh55.easymvvm.mvvm.BaseViewModel

class MainViewModel : BaseViewModel() {

    val isShow = true
    val intentText get() = getArgumentsIntent()?.getStringExtra("text")
    var indexContent = MutableLiveData<String>()



    fun clickHello(){
        startActivityForResult(UserActivity::class.java)
    }

}