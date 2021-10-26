package com.ex.dd_mini_asst.utils

import android.app.ProgressDialog
import android.content.Context
import com.ex.dd_mini_asst.R

object ProgressBarUtil {
    /**Object class for progress dialog**/
    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
            it.setContentView(R.layout.progress_dialog)
            it.isIndeterminate = true
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }
}