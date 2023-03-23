package com.mh55.easy.App

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.blankj.utilcode.util.AppUtils
import com.mh55.easy.utils.LogUtil
import okhttp3.internal.and
import java.lang.Exception
import java.lang.StringBuilder
import java.security.MessageDigest

/**
 *   公司名称: ~漫漫人生路~总得错几步~
 *   创建作者: Android 孟从伦
 *   创建时间: 2021/11/26
 *   功能描述:  代码方式获取 32位MD5签名(字母小写  不含 : 号)
 *   使用Android Studio 获取签名:(字母大写  有 : 号)
 *   Terminal ==> keytool -list -v -keystore fanzairecovery.jks
 *                输入签名密码
 */
object SignTool {

    //调用示例
    //SignTool.printSignatureMD5(CHAuthService.this,"com.sccngitv.dvb");
    /**
     * App 基础信息
     */
    fun printSignatureMD5(
        mContext: Context
    ) {

    }

    @SuppressLint("WrongConstant")
    private fun getMD5MessageDigest(mContext: Context, str: String? = AppUtil.AppInfo.getPackageName()): String {
        return try {
            var i = 0
            val signature = mContext.packageManager.getPackageInfo(str!!, 64).signatures[0]
            val instance = MessageDigest.getInstance("md5")
            instance.update(signature.toByteArray())
            val digest = instance.digest()
            val stringBuilder = StringBuilder()
            val length = digest.size
            while (i < length) {
                var toHexString = Integer.toHexString(digest[i] and 255)
                if (toHexString.length == 1) {
                    val stringBuilder2 = StringBuilder()
                    stringBuilder2.append("0")
                    stringBuilder2.append(toHexString)
                    toHexString = stringBuilder2.toString()
                }
                stringBuilder.append(toHexString)
                i++
            }
            stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "null"
        }
    }
}