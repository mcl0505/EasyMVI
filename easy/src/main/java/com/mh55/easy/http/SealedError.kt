package com.mh55.easy.http

import com.mh55.easy.R
import com.mh55.easy.ext.getString

sealed class SealedError(val code: String, val error: String) {
    data class CancelHttp(private val msgError: String = R.string.mv_net_cancel.getString()) : SealedError(ResponseCode.Code_CancelHttp, msgError)

    data class Unknown(private val msgError: String = R.string.mv_net_unknown.getString()) : SealedError(ResponseCode.Code_Unknown, msgError)

    data class ParseError(private val msgError: String = R.string.mv_net_parse.getString()) : SealedError(ResponseCode.Code_ParseError, msgError)

    data class NetworkError(private val msgError: String = R.string.mv_net_network.getString()) : SealedError(ResponseCode.Code_NetworkError, msgError)

    data class SslError(private val msgError: String = R.string.mv_net_ssl.getString()) : SealedError(ResponseCode.Code_SslError, msgError)

    data class TimeoutError(private val msgError: String = R.string.mv_net_timeout.getString()) : SealedError(ResponseCode.Code_TimeoutError, msgError)
}