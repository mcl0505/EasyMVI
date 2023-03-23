package com.mh55.demo.view_model

import androidx.lifecycle.MutableLiveData
import com.mh55.demo.UserActivity
import com.mh55.easy.mvvm.BaseViewModel

class MainViewModel : BaseViewModel() {

    val isShow = true
    val intentText get() = getArgumentsIntent()?.getStringExtra("text")
    var indexContent = MutableLiveData<String>()



    fun clickHello(){
        startActivityForResult(UserActivity::class.java)
    }

}