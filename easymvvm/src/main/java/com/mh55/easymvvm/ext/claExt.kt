package com.mh55.easymvvm.ext

import java.lang.reflect.ParameterizedType

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any,position:Int): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[position] as VM
}
