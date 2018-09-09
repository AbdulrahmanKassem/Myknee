package com.medical.myknee.classes

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Fragment
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Toast
import com.medical.myknee.R

open class BaseFragment : Fragment() {
    var dialog: AlertDialog?=null
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showprogress(){
        val builder = AlertDialog.Builder(this.context)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.progress_dialog)
        dialog = builder.create()
        dialog!!.show()
    }
    fun dismissProgress(){
        if (dialog!=null)
            dialog!!.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showLongToast(strMsg: String, intMsg: Int = 0) {
        if (!strMsg.isEmpty()) {
            Toast.makeText(this.context, strMsg, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this.context, intMsg, Toast.LENGTH_LONG).show()
        }
    }
}