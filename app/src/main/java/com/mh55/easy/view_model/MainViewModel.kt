package com.mh55.easy.view_model

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.mh55.easy.UserActivity
import com.mh55.easymvvm.mvvm.BaseViewModel

class MainViewModel : BaseViewModel() {

    val isShow = true
    var buttomText  = MutableLiveData("跳转")

    fun clickHello(){
        startActivityForResult(UserActivity::class.java)
    }

    override fun onActivityResultOk(intent: Intent) {
        super.onActivityResultOk(intent)
        val  text = intent.getStringExtra("title")?:""
        buttomText.postValue(text)
    }
}