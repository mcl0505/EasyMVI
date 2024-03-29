package com.mh55.easy.ext

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 *   if  else  代替
 */


@OptIn(ExperimentalContracts::class)
inline fun Boolean?.yes(block: () -> Unit): Boolean? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (this == true) block()
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun Boolean?.no(block: () -> Unit): Boolean? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (this != true) block()
    return this
}