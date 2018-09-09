package com.medical.myknee.specialized

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.medical.myknee.Interface.GetTime
import com.medical.myknee.model.ExerciseDate
import com.medical.myknee.R
import com.medical.myknee.classes.BaseActivity
import com.medical.myknee.classes.TimePickerFragment
import kotlinx.android.synthetic.main.activity_exercise_time.*

class SelectExerciseTime : BaseActivity(), GetTime {

    private var mAuth: FirebaseAuth? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_time)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        mAuth = FirebaseAuth.getInstance()


        btnMorningTime.setOnClickListener {
            var newFragment = TimePickerFragment(2)
            newFragment.time = this
            newFragment.show(supportFragmentManager, "timePicker")
        }

        btnEveningTime.setOnClickListener {
            var newFragment = TimePickerFragment(1)
            newFragment.time = this
            newFragment.show(supportFragmentManager, "timePicker")
        }



        btnSaveSchedule.setOnClickListener {

            if (edTimer.text.toString().trim().toInt() in 10..30) {
                for (i in 0 until MainSpecialized.instance.trainingWeeks!![intent.getIntExtra("position", 0)].days!!.size) {
                    MainSpecialized.instance.trainingWeeks!![intent.getIntExtra("position", 0)].days!![i].timeOfExerciseInDay!!.timer = edTimer.text.toString().trim().toInt()
                }
                showprogress()
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("users").child(this@SelectExerciseTime.intent.getStringExtra("uid"))
                        .child("trainingWeeks")

                myRef.setValue(MainSpecialized.instance.trainingWeeks) { error, _ ->
                    if (error == null) {
                        dismissProgress()
                        showLongToast("تم الحفظ")
                        this.finish()
                    } else {
                        dismissProgress()
                        showLongToast("خطاء في رفع البيانات, تحقق من الشبكه")

                    }
                }
            } else {
                edTimer.error = "يجب ان يكون العدد ما بين 30 و 10"
            }

        }

    }

    override fun timeEveningExercise(year: Int, month: Int, day: Int, hour: Int, minute: Int) {

        var defaultExerciseTime = ExerciseDate()
        defaultExerciseTime.year = year
        defaultExerciseTime.month = month
        defaultExerciseTime.day = day
        defaultExerciseTime.hour = hour
        defaultExerciseTime.minute = minute


        for (i in 0 until intent.getIntExtra("position", 0)) {
            defaultExerciseTime = checkDayAndMonth(defaultExerciseTime.year,
                    defaultExerciseTime.month, defaultExerciseTime.day + 7, defaultExerciseTime.hour,
                    defaultExerciseTime.minute)
        }
        for (i in 0 until MainSpecialized.instance.trainingWeeks!![intent.getIntExtra("position", 0)].days!!.size) {
            val exerciseTime = checkDayAndMonth(defaultExerciseTime.year, defaultExerciseTime.month,
                    defaultExerciseTime.day + i, defaultExerciseTime.hour, defaultExerciseTime.minute)
            MainSpecialized.instance.trainingWeeks!![intent.getIntExtra("position", 0)].days!![i].timeOfExerciseInDay!!.exerciseEvening = exerciseTime


        }
    }

    override fun timemorningExercise(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        var defaultExerciseTime = ExerciseDate()
        defaultExerciseTime.year = year
        defaultExerciseTime.month = month
        defaultExerciseTime.day = day
        defaultExerciseTime.hour = hour
        defaultExerciseTime.minute = minute


        for (i in 0 until intent.getIntExtra("position", 0)) {
            defaultExerciseTime = checkDayAndMonth(defaultExerciseTime.year,
                    defaultExerciseTime.month, defaultExerciseTime.day + 7, defaultExerciseTime.hour,
                    defaultExerciseTime.minute)
        }

        for (i in 0 until MainSpecialized.instance.trainingWeeks!![intent.getIntExtra("position", 0)].days!!.size) {
            val exerciseTime = checkDayAndMonth(defaultExerciseTime.year, defaultExerciseTime.month,
                    defaultExerciseTime.day + i, defaultExerciseTime.hour, defaultExerciseTime.minute)
            MainSpecialized.instance.trainingWeeks!![intent.getIntExtra("position", 0)]
                    .days!![i].timeOfExerciseInDay!!.exerciseMorning = exerciseTime

        }
    }

    fun checkDayAndMonth(year: Int, month: Int, day: Int, hour: Int, minute: Int): ExerciseDate {
        var exerciseTime = ExerciseDate()

        if (day > 31 && month == 0) {
            exerciseTime.year = year
            exerciseTime.month = 1
            exerciseTime.day = day - 31
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (month == 1 && day > 29 && month % 4 == 0) {
            exerciseTime.year = year
            exerciseTime.month = 2
            exerciseTime.day = day - 29
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (month == 1 && day > 28 && month % 4 != 0) {
            exerciseTime.year = year
            exerciseTime.month = 2
            exerciseTime.day = day - 28
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (day > 31 && month == 2) {
            exerciseTime.year = year
            exerciseTime.month = 3
            exerciseTime.day = day - 31
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (day > 30 && month == 3) {
            exerciseTime.year = year
            exerciseTime.month = 4
            exerciseTime.day = day - 30
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (day > 31 && month == 4) {
            exerciseTime.year = year
            exerciseTime.month = 5
            exerciseTime.day = day - 31
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (day > 30 && month == 5) {
            exerciseTime.year = year
            exerciseTime.month = 6
            exerciseTime.day = day - 30
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (day > 31 && month == 6) {
            exerciseTime.year = year
            exerciseTime.month = 7
            exerciseTime.day = day - 31
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (day > 31 && month == 7) {
            exerciseTime.year = year
            exerciseTime.month = 8
            exerciseTime.day = day - 31
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (day > 30 && month == 8) {
            exerciseTime.year = year
            exerciseTime.month = 9
            exerciseTime.day = day - 30
            exerciseTime.hour = hour
        } else if (day > 31 && month == 9) {
            exerciseTime.year = year
            exerciseTime.month = 10
            exerciseTime.day = day - 31
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else if (day > 30 && month == 10) {
            exerciseTime.year = year
            exerciseTime.month = 11
            exerciseTime.day = day - 30
            exerciseTime.hour = hour
        } else if (day > 31 && month == 11) {
            exerciseTime.year = year + 1
            exerciseTime.month = 0
            exerciseTime.day = day - 31
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        } else {
            exerciseTime.year = year
            exerciseTime.month = month
            exerciseTime.day = day
            exerciseTime.hour = hour
            exerciseTime.minute = minute
        }

        return exerciseTime
    }

}
