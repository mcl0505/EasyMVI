package com.mh55.easymvvm.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
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
    lateinit var mActivity: AppCompatActivity
    lateinit var mDialogBinding: DB
    lateinit var mViewModel: VM

    protected lateinit var mRootView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = createViewModel()
        main(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as AppCompatActivity
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDialogBinding = bindingInflate(inflater,layoutId,container)
        mDialogBinding.lifecycleOwner = this
        return mDialogBinding.root
    }

    //执行在Fragment 的 onGetLayoutInflater 中
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mRootView = LayoutInflater.from(mContext).inflate(layoutId, null)
        val dialog = Dialog(mContext!!, getDialogStyle())
        dialog.setContentView(mRootView)
        dialog.setCancelable(canCancel())
        dialog.setCanceledOnTouchOutside(canCancel())
        val window = dialog.window
        window!!.setGravity(setGravity())
        window.setWindowAnimations(getDialogAnim())
        window.setLayout(setWidth(), setHeight())
        return dialog
    }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        main(savedInstanceState)
        if (::onViewClick.isInitialized)onViewClick.invoke(mDialogBinding)

    }

    /**
     * 处理具体的业务逻辑
     * @param savedInstanceState
     */
    open fun main(savedInstanceState: Bundle?) {}


    /**
     * 弹框进入与消失动画
     */
    open fun getDialogAnim(): Int = R.style.dialogAnimBottom

    protected open fun canCancel(): Boolean {
        return true
    }


    open fun show(manager: FragmentManager?) {
        super.show(manager!!, TAG)
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }
}