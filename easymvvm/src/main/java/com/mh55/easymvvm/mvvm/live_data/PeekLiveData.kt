package com.mh55.easymvvm.mvvm.live_data

/**
 * 公司名称：~漫漫人生路~总得错几步~
 * 创建作者：Android 孟从伦
 * 创建时间：2022/10/3
 * 功能描述：包装类
 */

open class PeekLiveData<T> : ProtectedUnPeekLiveData<T>() {
    public override fun setValue(value: T?) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    open class Builder<T> {
        /**
         * 是否允许传入 null value
         */
        private var isAllowNullValue = false

        fun setAllowNullValue(isAllowNullValue: Boolean): Builder<T> {
            this.isAllowNullValue = isAllowNullValue
            return this
        }

        fun create(): PeekLiveData<T> {
            val liveData = PeekLiveData<T>()
            liveData.isAllowNullValue = this.isAllowNullValue
            return liveData
        }

    }
}