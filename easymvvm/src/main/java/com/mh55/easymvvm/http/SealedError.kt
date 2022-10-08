package com.mh55.easymvvm.http

import com.mh55.easymvvm.EasyApplication
import com.mh55.easymvvm.R
import com.mh55.easymvvm.ext.getString

sealed class SealedError(val code: Int, val error: String) {
    data class Unknown(
        private val msgError: String = R.string.mv_net_unknown.getString()
    ) : SealedError(1000, msgError)

    data class ParseError(
        private val msgError: String = R.string.mv_net_parse.getString()
    ) :
        SealedError(1001, msgError)

    data class NetworkError(
        private val msgError: String = R.string.mv_net_network.getString()
    ) :
        SealedError(1002, msgError)

    data class SslError(
        private val msgError: String = R.string.mv_net_ssl.getString()
    ) : SealedError(1003, msgError)

    data class TimeoutError(
        private val msgError: String = R.string.mv_net_timeout.getString()
    ) :
        SealedError(1004, msgError)
}