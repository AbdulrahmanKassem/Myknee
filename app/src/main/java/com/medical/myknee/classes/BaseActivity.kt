package com.medical.myknee.classes

import android.app.AlertDialog
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.medical.myknee.R

open class BaseActivity : AppCompatActivity() {
    var dialog:AlertDialog?=null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showprogress(){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.progress_dialog)
         dialog = builder.create()
        dialog!!.show()
    }
    fun dismissProgress(){
        if (dialog!=null)
        dialog!!.dismiss()
    }

    fun showLongToast(strMsg: String, intMsg: Int = 0) {
        if (!strMsg.isEmpty()) {
            Toast.makeText(this, strMsg, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, intMsg, Toast.LENGTH_LONG).show()
        }
    }
}