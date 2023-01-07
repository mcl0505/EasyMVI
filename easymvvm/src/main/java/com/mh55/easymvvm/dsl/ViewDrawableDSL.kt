package com.mh55.easymvvm.dsl

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.mh55.easymvvm.ext.toDp

/**
 *   公司名称: ~漫漫人生路~总得错几步~
 *   创建作者: Android 孟从伦
 *   创建时间: 2022/04/02
 *   功能描述:
 */


inline fun View.setShapeDrawable(init: DslViewDrawableBuilder.() -> Unit) {
    //具体实现类
    val drawableBuilder = DslViewDrawableBuilderImpl()
    drawableBuilder.init()
    this.background = drawableBuilder.build()
}

//添加操作接口
interface DslViewDrawableBuilder {
    fun shape(method: (DslShareBuilder.() -> Unit)? = null)
}

//属性
interface DslShareBuilder {
    /**
     * 设置背景颜色
     */
    fun addSolidColor(color: Int)

    /**
     * 设置圆角
     */
    fun addCornerSize(size: Float)

    /**
     * 设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
     */
    fun addCornerRadii(size: FloatArray)

    /**
     * 边框
     */
    fun addStroke(size: Int,color: Int)

    /**
     * 线性渐变
     * @param colorArray 颜色数组
     * @param gradientType 渐变方式
     *        LEFT_RIGHT ===>  左到右  颜色数组中的顺序即是渐变顺序
     *        RIGHT_LEFT ===>  右到左  颜色数组中的顺序即是渐变顺序
     *        BOTTOM_TOP ===>  下到上  颜色数组中的顺序即是渐变顺序
     *        TOP_BOTTOM ===>  上到下  颜色数组中的顺序即是渐变顺序
     */
    fun addGradient(colorArray: IntArray,gradientType:GradientDrawable.Orientation?=GradientDrawable.Orientation.LEFT_RIGHT)
}

//属性实现
class DslShareBuilderImp : DslShareBuilder {

    var mViewSolidColor: Int = Color.parseColor("#00000000")
    var mViewCornerSize: Float? = 0f
    var mViewCornerRadii: FloatArray? = floatArrayOf()
    var mViewGradient: IntArray? = intArrayOf() //渐变颜色值
    var mViewGradientType: GradientDrawable.Orientation? = GradientDrawable.Orientation.RIGHT_LEFT
    var mViewStrokeSize: Int? = 0
    var mViewStrokeColor: Int? = Color.parseColor("#00000000")

    override fun addSolidColor(color: Int) {
        mViewSolidColor = color
    }

    override fun addCornerSize(size: Float) {
        mViewCornerSize = size
    }

    override fun addCornerRadii(size: FloatArray) {
        mViewCornerRadii = size
    }

    override fun addStroke(size: Int,color: Int) {
        mViewStrokeSize = size
        mViewStrokeColor = color
    }

    override fun addGradient(colorArray: IntArray,gradientType:GradientDrawable.Orientation?) {
        mViewGradient = colorArray
        mViewGradientType = gradientType
    }

}

class DslViewDrawableBuilderImpl : DslViewDrawableBuilder {
    private val drawable = GradientDrawable()

    fun build(): GradientDrawable {
        return drawable
    }

    override fun shape(method: (DslShareBuilder.() -> Unit)?) {
        val mShareBuilder = DslShareBuilderImp()
        method?.let { mShareBuilder.it() }

        //设置不同的圆角
        if (mShareBuilder.mViewCornerRadii?.size!!>0){
            drawable.cornerRadii = mShareBuilder.mViewCornerRadii!!
        }else {
            drawable.cornerRadius = mShareBuilder.mViewCornerSize!!.toDp()
        }

        //设置背景色
        if (mShareBuilder.mViewGradient?.size!!>0){
            //渐变背景
            drawable.orientation = mShareBuilder.mViewGradientType
            drawable.colors = mShareBuilder.mViewGradient!!
        }else {
            //村色背景
            drawable.setColor(mShareBuilder.mViewSolidColor)
        }

        //设置按钮边框
        if (mShareBuilder.mViewStrokeSize!!>0){
            drawable.setStroke(mShareBuilder.mViewStrokeSize!!.toDp(),mShareBuilder.mViewStrokeColor!!)
        }

    }


}