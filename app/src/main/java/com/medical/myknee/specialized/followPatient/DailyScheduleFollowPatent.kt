package com.medical.myknee.specialized.followPatient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.medical.myknee.R
import com.medical.myknee.adapter.DailyScheduleInterAdapter
import com.medical.myknee.model.Day
import kotlinx.android.synthetic.main.activity_daily_schedule_follow_patent.*

class DailyScheduleFollowPatent : AppCompatActivity() {
    private var dailyScheduleAdapter: DailyScheduleInterAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_schedule_follow_patent)
        rvDays!!.layoutManager = LinearLayoutManager(this)
        dailyScheduleAdapter = DailyScheduleInterAdapter(context = this@DailyScheduleFollowPatent,
                daysArray = intent.getSerializableExtra("days") as ArrayList<Day>,
                weekName = intent.getStringExtra("weekName"))
        rvDays!!.adapter = dailyScheduleAdapter
        dailyScheduleAdapter!!.notifyDataSetChanged()
    }
}
