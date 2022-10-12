package com.mh55.easymvvm.App

import android.os.Build
import com.mh55.easymvvm.App.ConfigBuilder.Path.CRASH_LOG_PATH
import com.mh55.easymvvm.EasyApplication
import com.mh55.easymvvm.utils.DateUtil
import java.io.*
import java.lang.Thread.UncaughtExceptionHandler
import kotlin.system.exitProcess

/**
 * 崩溃信息捕获，存储在 /sdcard/Android/data/xxx.xxx.xxx/cache/Log/crash/日期.log
 */
internal object CrashHandlerUtil : UncaughtExceptionHandler {

    fun init() {
        Thread.setDefaultUncaughtExceptionHandler(this)

        val file = File(CRASH_LOG_PATH)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (ConfigBuilder.isOpenCarsh){
            handleException(ex)
        }

        ex.printStackTrace()
        exitProcess(0)
    }

    private fun formatLogInfo(ex: Throwable): String {
        val lineSeparator = "\r\n"

        val sb = StringBuilder()
        val logTime = "logTime:" + DateUtil.formatYMDHMS_()

        val exception = "exception:$ex"

        val info = StringWriter()
        val printWriter = PrintWriter(info)
        ex.printStackTrace(printWriter)

        val dump = info.toString()

        val crashDump = "crashDump:{$dump}"
        printWriter.close()

        sb.append(lineSeparator)
        sb.append("&start---").append(lineSeparator)
        sb.append(logTime).append(lineSeparator)
        sb.append("appVerName:").append(AppUtil.getAppVersionName(EasyApplication.instance)).append(lineSeparator)
        sb.append("appVerCode:").append(AppUtil.getAppVersionCode(EasyApplication.instance)).append(lineSeparator)
        // 系统版本
        sb.append("OsVer:").append(Build.VERSION.RELEASE).append(lineSeparator)
        // 手机厂商
        sb.append("vendor:").append(Build.MANUFACTURER).append(lineSeparator)
        // 型号
        sb.append("model:").append(Build.MODEL).append(lineSeparator)
        sb.append(exception).append(lineSeparator)
        sb.append(crashDump).append(lineSeparator)
        sb.append("&end---").append(lineSeparator).append(lineSeparator).append(lineSeparator)

        return sb.toString()
    }

    private fun handleException(ex: Throwable) {
        try {

            FileOutputStream(
                    CRASH_LOG_PATH + DateUtil.getNowString() + ".log", true).use { outputStream ->
                outputStream.write(formatLogInfo(ex).toByteArray())
                outputStream.write("\n".toByteArray())
                outputStream.write("\n".toByteArray())
                outputStream.flush()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
