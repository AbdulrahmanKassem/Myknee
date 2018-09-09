package com.medical.myknee.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.medical.myknee.adapter.WeeklyScheduleInterAdapter
import com.medical.myknee.classes.Alarm
import com.medical.myknee.classes.BaseFragment
import com.medical.myknee.model.ArrayListWeek
import com.medical.myknee.R
import kotlinx.android.synthetic.main.fragment_exercises.*
import java.util.*


class ExercisesFragment : BaseFragment() {
    private var mAuth: FirebaseAuth? = null
    private var mDatabase: DatabaseReference? = null
    var weekAdapter: WeeklyScheduleInterAdapter? = null
    var listWeek: ArrayListWeek? = ArrayListWeek()
    var alramThisDay=0
    object Holder {
        val instance = ExercisesFragment()
    }

    companion object {
        val instance by lazy { Holder.instance }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvWeek!!.layoutManager = LinearLayoutManager(this.context)
        weekAdapter = WeeklyScheduleInterAdapter(context = this@ExercisesFragment.context, weekArray = listWeek!!.trainingWeeks)
        rvWeek!!.adapter = weekAdapter
        weekAdapter!!.notifyDataSetChanged()

        mAuth = FirebaseAuth.getInstance()
        if (listWeek!!.trainingWeeks!!.isEmpty()) {
            this@ExercisesFragment.showprogress()
            mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(mAuth!!.uid.toString())
            mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listWeek = dataSnapshot.getValue(ArrayListWeek::class.java)
                    weekAdapter = WeeklyScheduleInterAdapter(context = this@ExercisesFragment.context, weekArray = listWeek!!.trainingWeeks)
                    weekAdapter!!.notifyDataSetChanged()
                    rvWeek!!.adapter = weekAdapter
                    this@ExercisesFragment.dismissProgress()
                    createCalendar()

                }


            })
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun createCalendar() {
        if (listWeek!!.trainingWeeks!!.isNotEmpty()) {
            text.visibility = View.VISIBLE
            tvISEmpty.visibility = View.GONE
            for (i in 0 until listWeek!!.trainingWeeks!!.size) {
                for (j in 0 until listWeek!!.trainingWeeks!![i].days!!.size) {
                    if (listWeek!!.trainingWeeks!![i].days!![j].timeOfExerciseInDay!!.exerciseEvening != null ) {

                        val exerciseDate2 = listWeek!!.trainingWeeks!![i].days!![j].timeOfExerciseInDay!!.exerciseEvening
                        val calendar2 = Calendar.getInstance()
                        calendar2.set(exerciseDate2!!.year, exerciseDate2.month, exerciseDate2.day,
                                exerciseDate2.hour, exerciseDate2.minute, 0)
                        setAlarm(calendar2.timeInMillis)

                    }
                    if (listWeek!!.trainingWeeks!![i].days!![j].timeOfExerciseInDay!!.exerciseMorning != null){
                        val exerciseDate = listWeek!!.trainingWeeks!![i].days!![j].timeOfExerciseInDay!!.exerciseMorning
                        val calendar = Calendar.getInstance()
                        calendar.set(exerciseDate!!.year, exerciseDate.month, exerciseDate.day,
                                exerciseDate.hour, exerciseDate.minute, 0)
                        setAlarm(calendar.timeInMillis)
                    }

                }
            }

        } else {
            tvISEmpty.visibility = View.VISIBLE
            text.visibility = View.GONE

        }

    }

    var countAlarm=0

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarm(time: Long) {
        val calendar = Calendar.getInstance()
        if (time > calendar.timeInMillis) {
            if (countAlarm<2){
                countAlarm++
            val am = this.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            val intent = Intent(this@ExercisesFragment.context, Alarm::class.java)
            val _id = System.currentTimeMillis().toInt()
            val appIntent = PendingIntent.getBroadcast(this@ExercisesFragment.context,
                    time.toInt(), intent, PendingIntent.FLAG_ONE_SHOT)
            am!!.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, appIntent)
        }
        }
    }


}
