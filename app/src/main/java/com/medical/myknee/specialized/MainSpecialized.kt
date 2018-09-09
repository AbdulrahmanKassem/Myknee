package com.medical.myknee.specialized

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.medical.myknee.adapter.WeeksAdapter
import com.medical.myknee.model.Day
import com.medical.myknee.model.Week
import com.medical.myknee.R
import kotlinx.android.synthetic.main.activity_main_specialized.*

open class MainSpecialized : AppCompatActivity() {
    open var trainingWeeks: ArrayList<Week>? = ArrayList()


    companion object {
        @JvmStatic
        lateinit var instance: MainSpecialized
    }

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_specialized)
        weeks()
        rcWeeks!!.layoutManager = LinearLayoutManager(this)
        var weekAdapter=WeeksAdapter(context = this, weekArray = trainingWeeks,
               uid =  this@MainSpecialized.intent.getStringExtra("uid"))
        rcWeeks!!.adapter = weekAdapter
    }



    private fun days(): ArrayList<Day> {
        var day1 = Day()
        day1.dayName = "اليوم الاول"
        var day2 = Day()
        day2.dayName = "اليوم الثاني"
        var day3 = Day()
        day3.dayName = "اليوم الثالث"
        var day4 = Day()
        day4.dayName = "اليوم الرابع"
        var day5 = Day()
        day5.dayName = "اليوم الخامس"
        var day6 = Day()
        day6.dayName = "اليوم السادس"
        var day7 = Day()
        day7.dayName = "اليوم السابع"
        var week=ArrayList<Day>()
        week.add(day1)
        week.add(day2)
        week.add(day3)
        week.add(day4)
        week.add(day5)
        week.add(day6)
        week.add(day7)

   return week}

    private fun weeks(){
        var week = Week()
        week.weekName = "الاسبوع الاول"
        week.days= days()
        trainingWeeks!!.add(week)

        var week2 = Week()
        week2.weekName = "الاسبوع الثاني"
        week2.days= days()
        trainingWeeks!!.add(week2)

        var week3 = Week()
        week3.weekName = "الاسبوع الثالث"
        week3.days= days()
        trainingWeeks!!.add(week3)

        var week4 = Week()
        week4.weekName = "الاسبوع الرابع"
        week4.days= days()
        trainingWeeks!!.add(week4)

        var week5 = Week()
        week5.weekName = "الاسبوع الخامس"
        week5.days= days()
        trainingWeeks!!.add(week5)

        var week6 = Week()
        week6.weekName = "الاسبوع السادس"
        week6.days= days()
        trainingWeeks!!.add(week6)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}
