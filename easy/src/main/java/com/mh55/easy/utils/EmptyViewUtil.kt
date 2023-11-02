package com.mh55.easy.utils

import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.widget.TextView
import android.text.TextUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mh55.easy.R
import com.mh55.easy.ext.getDrawable

/**
 * 暂无数据
 *
 * @ClassName:EmptyViewUtil
 * @PackageName:com.lvyinnengchen.app.utils
 * @Create On 2021/1/29 0029   2021/1/29 0029
 * @Site:http://www.handongkeji.com
 * @author:pengjingjing
 * @Copyrights 2021/1/29 0029 handongkeji All rights reserved.
 */
object EmptyViewUtil {
    fun setEmptyView(
        context: Context,
        recyclerView: RecyclerView,
        mDrawable: Int = R.drawable.empty_pic_data,
        desc: String = ""
    ): View {
        val inflate =
            LayoutInflater.from(context).inflate(R.layout.view_no_data, recyclerView, false)
        if (!TextUtils.isEmpty(desc)) {
            val tvDesc = inflate.findViewById<TextView>(R.id.no_data_tv)
            tvDesc.text = desc
        }
        if (mDrawable != null) {
            val imgDesc = inflate.findViewById<ImageView>(R.id.img_no_data)
            imgDesc.setImageDrawable(mDrawable.getDrawable())
        }
        return inflate
    }


}