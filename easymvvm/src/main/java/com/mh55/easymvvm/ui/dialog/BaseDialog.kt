package com.mh55.easymvvm.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.kckj.baselibrary.ext.bindingInflate
import com.mh55.easymvvm.R
import com.mh55.easymvvm.ext.getVmClazz
import com.mh55.easymvvm.mvvm.BaseViewModel

open class BaseDialog<DB : ViewDataBinding,VM : BaseViewModel,>(val layoutId: Int) : DialogFragment() {
    @JvmField
    protected var TAG = this.javaClass.simpleName

    lateinit var onViewClick:(mView:DB)->Unit

    @JvmField
    protected var mContext: Context? = null
    lateinit var mDialogBinding: DB
    lateinit var mViewModel: VM

    protected lateinit var mRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,getDialogStyle())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        mViewModel = createViewModel()
        dialog?.let {
            it.setCancelable(canCancel())
            it.setCanceledOnTouchOutside(canCancel())
            //禁止返回关闭弹框可在此处处理
            if (!canCancel()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    it.setOnKeyListener(object : DialogInterface.OnKeyListener  {
                        override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                            if (keyCode == 4) return true
                            return false
                        }
                    });
                }
            }
        }

        mDialogBinding = bindingInflate(inflater,layoutId,container)
        mDialogBinding.lifecycleOwner = this
        initData()
        return mDialogBinding.root
    }

    //点击手机返回
    fun onKeyBack(){}

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window!!.setGravity(setGravity())
        window.setWindowAnimations(getDialogAnim())
        window.setLayout(setWidth(), setHeight())
    }

    fun initData(){}

    open fun getDialogStyle(): Int = R.style.dialog_style

    protected open fun setWidth(): Int {
        return LinearLayout.LayoutParams.MATCH_PARENT
    }

    protected open fun setHeight(): Int {
        return LinearLayout.LayoutParams.WRAP_CONTENT
    }

    protected open fun setGravity(): Int {
        return Gravity.BOTTOM
    }

    /**
     * 弹框进入与消失动画
     */
    private fun getDialogAnim(): Int = when(setGravity()){
        Gravity.CENTER->R.style.dialogAnimCenter
        Gravity.BOTTOM->R.style.dialogAnimBottom
        Gravity.LEFT->R.style.dialogAnimLeft
        Gravity.RIGHT->R.style.dialogAnimRight
        else-> R.style.dialogAnimBottom
    }

    protected open fun canCancel(): Boolean {
        return true
    }

    open fun show(manager: FragmentManager?) {
        kotlin.runCatching {
            manager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
//            super.show(manager!!, TAG)
            manager?.beginTransaction()?.add(this,TAG)?.commitAllowingStateLoss()
        }

    }


    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }
}