package com.mh55.easy.http

class ResponseThrowable : Exception {
    /**
     * 错误码
     */
    var errorCode: String = "0"

    /**
     * 错误信息
     */
    var errorMsg: String?

    /**
     * 错误日志
     */
    var errorLog: String?

    constructor(errorCode: String, errorMsg: String?, errorLog: String? = "") : super(errorMsg) {
        this.errorCode = errorCode
        this.errorMsg = errorMsg
        this.errorLog = errorLog
    }

    constructor(sealedError: SealedError, errorLog: String? = "") {
        this.errorLog = errorLog
        this.errorCode = sealedError.code
        this.errorMsg = sealedError.error
    }
}