package com.ex.dd_mini_asst.ui.base

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ex.dd_mini_asst.DDApplication
import com.ex.dd_mini_asst.R
import com.ex.dd_mini_asst.listeners.ConnectivityReceiverListener
import com.ex.dd_mini_asst.listeners.ProgressBarInterface
import com.ex.dd_mini_asst.utils.ProgressBarUtil
import com.google.android.material.snackbar.Snackbar

open abstract class BaseAct : AppCompatActivity(),ConnectivityReceiverListener,ProgressBarInterface {

    /**SnackBar initilization**/
    private var mSnackBar: Snackbar? = null

    /**Progressbar initilization**/
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onResume() {
        super.onResume()
        DDApplication.appInstance?.setNetWorkListener(this)
    }
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected, getString(R.string.your_offline))
    }

    override fun showProgress() {
        try {
            hideProgress()
            mProgressDialog = ProgressBarUtil.showLoadingDialog(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun hideProgress() {
        mProgressDialog?.let { if (it.isShowing) it.dismiss() }
    }

    private fun showMessage(isConnected: Boolean, message: String) {
        if (!isConnected) {
            mSnackBar = Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
            ) //Assume "rootLayout" as the root layout of every activity.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mSnackBar?.setTextColor(resources.getColor(R.color.white, null))
            }else{
                mSnackBar?.setTextColor(resources.getColor(R.color.white))
            }

            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()

        } else {
            mSnackBar?.dismiss()

        }
    }
}