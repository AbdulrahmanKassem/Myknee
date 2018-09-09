package com.medical.myknee.intern

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.TextView
import com.medical.myknee.R
import com.medical.myknee.model.Week
import kotlinx.android.synthetic.main.activity_ready_to_exercise.*


class ReadyToExercise : AppCompatActivity(), View.OnClickListener {


    var timer=0
    var arrayTraining: ArrayList<Int>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready_to_exercise)
        arrayTraining = intent.getIntegerArrayListExtra("arrayTraining")
        timer= intent.getIntExtra("timer",0)
        btnNotReady.setOnClickListener(this)
        btnReady.setOnClickListener(this)

    }


    override fun onClick(view: View?) {
        when (view) {
            btnReady -> {
                var intent=Intent(view!!.context,StartExercise::class.java)
                intent.putExtra("arrayTraining",arrayTraining)
                intent.putExtra("timer", timer)
                intent.putExtra("position", this@ReadyToExercise.intent.getIntExtra("position",0))
                intent.putExtra("weekArray", this@ReadyToExercise.intent.getSerializableExtra("weekArray")as ArrayList<Week>)
                view.context.startActivity(intent)
                this@ReadyToExercise.finish()

            }

            btnNotReady -> {

                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.because_dialog)

                val tired = dialog.findViewById(R.id.tired) as TextView
                val dontWant = dialog.findViewById(R.id.dontWant) as TextView
                val anotherTime = dialog.findViewById(R.id.anotherTime) as TextView
                tired.setOnClickListener {
                    dialog.dismiss()
                    this@ReadyToExercise.finish()
                }

                dontWant.setOnClickListener {
                    dialog.dismiss()
                    this@ReadyToExercise.finish()
                }

                anotherTime.setOnClickListener {
                    dialog.dismiss()
                    this@ReadyToExercise.finish()
                }


                dialog.show()


            }
        }
    }
}
