package com.medical.myknee.intern

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.medical.myknee.R
import com.medical.myknee.classes.BaseActivity
import com.medical.myknee.model.Week
import com.medical.myknee.specialized.MainSpecialized
import kotlinx.android.synthetic.main.activity_start_exercise.*
import java.util.*


class StartExercise : BaseActivity() {
    var mp: MediaPlayer? = null
    var arrayTraining: ArrayList<Int>? = ArrayList()
    var counter = 0
    var updateHandler = Handler()
    var runnable: Runnable? = null
    var exerciseNumber = 0
    var timer = 0
    var position = 0
    var exerciseArray = ArrayList<Int>()
    private var mAuth: FirebaseAuth? = null

    var arratWeek: ArrayList<Week>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_exercise)
        createArrayImage()
        mAuth = FirebaseAuth.getInstance()
        arrayTraining = intent.getIntegerArrayListExtra("arrayTraining")
        timer = intent.getIntExtra("timer", 0)
        arratWeek = this@StartExercise.intent.getSerializableExtra("weekArray") as ArrayList<Week>
        position = this@StartExercise.intent.getIntExtra("position", 0)
        Glide.with(this)
                .load(exerciseArray[arrayTraining!![exerciseNumber]])
                .into(exerciseImage!!)
        mp = MediaPlayer.create(this, R.raw.counter)

        startTimer()

        btnNextOrFinish.setOnClickListener {
            if (btnNextOrFinish.text.toString().equals("التالي")) {
                Glide.with(this)
                        .load(exerciseArray[arrayTraining!![exerciseNumber]])
                        .into(exerciseImage!!)

                startTimer()
                btnNextOrFinish.visibility = View.GONE

            } else {
                var calendar = Calendar.getInstance()
                for (i in 0 until arratWeek!![position].days!!.size) {
                    if (calendar.get(Calendar.DAY_OF_MONTH) == arratWeek!![position].days!![i].timeOfExerciseInDay!!.exerciseEvening!!.day) {
                        arratWeek!![position].days!![i].isDo = true
                    }
                }
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("users").child(mAuth!!.currentUser!!.uid)
                        .child("trainingWeeks").child(position.toString()).child("days")

                myRef.setValue(arratWeek!![position].days) { error, _ ->
                    if (error == null) {
                        dismissProgress()
                        this@StartExercise.finish()
                    } else {
                        dismissProgress()
                        showLongToast("خطاء في رفع البيانات, تحقق من الشبكه")

                    }
                }
            }

        }


    }

    fun startTimer() {


        runnable = Runnable {
            if (!mp!!.isPlaying) {
                mp!!.start()
            }
            counter += 1
            tvTimer.text = "المؤقت الزمني : $counter"

            if (counter < timer) {
                updateHandler.postDelayed(runnable, 3000)
            } else {
                exerciseNumber++
                if (exerciseNumber < arrayTraining!!.size) {
                  takeBreak()
                } else {
                    btnNextOrFinish.text = "إنهاء"
                    btnNextOrFinish.setBackgroundResource(R.drawable.rounded4px_corners_fill_red)
                    btnNextOrFinish.visibility = View.VISIBLE

                }
            }
        }
        updateHandler.postDelayed(runnable, 1200)
    }

    private fun takeBreak(){
        counter = 30
        tvTimer.text = counter.toString()
        btnNextOrFinish.text = "التالي"
        btnNextOrFinish.setBackgroundResource(R.drawable.rounded4px_corners_fill_green)

        runnable = Runnable {
            counter -= 1
            tvTimer.text = "وقت الراحه : $counter"

            if (counter > 0) {
                updateHandler.postDelayed(runnable, 2200)
            } else {
                if (exerciseNumber < arrayTraining!!.size) {
                    btnNextOrFinish.visibility = View.VISIBLE
                }
            }
        }
        updateHandler.postDelayed(runnable, 1200)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateHandler.removeCallbacks(runnable)
       if (mp?.isPlaying!!){
           mp!!.stop()
       }
        this@StartExercise.finish()

    }

    fun createArrayImage(){

        exerciseArray.add(R.drawable.exercise1)
        exerciseArray.add(R.drawable.exercise2)
        exerciseArray.add(R.drawable.exercise3)
        exerciseArray.add(R.drawable.exercise4)
        exerciseArray.add(R.drawable.exercise5)
        exerciseArray.add(R.drawable.exercise6)
        exerciseArray.add(R.drawable.exercise7)
        exerciseArray.add(R.drawable.exercise8)
        exerciseArray.add(R.drawable.exercise9)
    }
}
