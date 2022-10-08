package com.mh55.easymvvm.http


/**
 * 公司名称：~漫漫人生路~总得错几步~
 * 创建作者：Android 孟从伦
 * 创建时间：2022/10/3
 * 功能描述：自定义异常内容
 */

abstract class BaseResponse<T> {

    /**
     * 通过服务器的Code码返回是否成功
     *
     * @return
     */
    abstract fun isSuccess(): Boolean

    /**
     * 服务器返回的Code
     *
     * @return Code值
     */
    abstract fun getResponseCode(): Int

    /**
     * 服务器返回的Data数据
     *
     * @return Data 数据
     */
    abstract fun getResponseData(): T?

    /**
     * 服务器返回的Message信息
     *
     * @return Message 信息
     */
    abstract fun getResponseMessage(): String
}