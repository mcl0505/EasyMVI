package com.mh55.easy.ext

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.mh55.easy.App.ConfigBuilder
import com.mh55.easy.R

//防止快速点击造成打开多个界面   只允许在 1秒内只能点击一次  single(2000){}   可自定义时间
/**
 * 防止快速点击造成打开多个界面
 */
fun <T : View> T.singleClick(time: Int = 500, block: (T) -> Unit) {
    this.setOnClickListener {
        val curClickTime = SystemClock.uptimeMillis()
        val lastClickTime = (it.getTag(R.id.singleClickId) as? Long) ?: 0
        // 超过点击间隔后再将lastClickTime重置为当前点击时间
        it.setTag(R.id.singleClickId, lastClickTime)
        if (curClickTime - lastClickTime >= time) {
            block(it as T)
        }
    }
}


/**
 * view 显示隐藏
 * @param show true:显示  false:不显示
 */
fun View.visibleOrGone(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

/**
 * view 显示与占位
 * @param show true:显示  false:不显示但是占位
 */
fun View.visibleOrInvisible(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

/**
 * 布局置灰处理
 * @param isGray true:正常  false:灰色
 */
fun View.changeGray(isGray:Boolean){
    //全局置灰处理
    val paint = Paint()
    val cm = ColorMatrix()
    cm.setSaturation(if (isGray) 0f else 1f)
    paint.colorFilter = ColorMatrixColorFilter(cm)
    this.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
}

/**
 * Palette可以提取的颜色如下:
● Vibrant （有活力的）
● Vibrant dark（有活力的 暗色）
● Vibrant light（有活力的 亮色）
● Muted （柔和的）
● Muted dark（柔和的 暗色）
● Muted light（柔和的 亮色）
 */
fun getPaletteColor(source: Any? = null, block: (mPalette: Palette) -> Unit) {
    var mBitmap: Bitmap? = null
    mBitmap = when (source) {
        is ImageView -> source.drawable?.toBitmap()!!
        is Int -> source.getDrawable()?.toBitmap()!!
        is Drawable -> source.toBitmap()
        is Bitmap -> source
        else -> ConfigBuilder.mImagePlaceholder.getDrawable()?.toBitmap()!!
    }
    Palette.Builder(mBitmap.copy(Bitmap.Config.ARGB_8888, true)!!).clearFilters().generate {
        if (it == null) {
            block.invoke(Palette.from(ConfigBuilder.mImagePlaceholder.getDrawable()?.toBitmap()!!).generate())
        } else {
            block.invoke(it)
        }
    }
}