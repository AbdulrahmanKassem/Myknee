package com.medical.myknee.specialized.followPatient

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.firebase.database.*
import com.medical.myknee.R
import com.medical.myknee.adapter.WeeklyScheduleFollowPatientAdapter
import com.medical.myknee.adapter.WeeklyScheduleInterAdapter
import com.medical.myknee.classes.BaseActivity
import com.medical.myknee.model.ArrayListWeek
import kotlinx.android.synthetic.main.activity_main_follow_patient.*

class MainFollowPatient : BaseActivity() {
    private var mDatabase: DatabaseReference? = null
    var weekAdapter: WeeklyScheduleFollowPatientAdapter? = null
    var listWeek: ArrayListWeek? = ArrayListWeek()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_follow_patient)


        this@MainFollowPatient.showprogress()
        mDatabase = FirebaseDatabase.getInstance().reference.child("users").
                child(intent.getStringExtra("userUid"))
        mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("trainingWeeks").exists()){
                                    listWeek = dataSnapshot.getValue(ArrayListWeek::class.java)
                weekAdapter = WeeklyScheduleFollowPatientAdapter(context = this@MainFollowPatient, weekArray = listWeek!!.trainingWeeks)
                weekAdapter!!.notifyDataSetChanged()
                rvWeek!!.adapter = weekAdapter
                    tvISEmpty.visibility=View.GONE
                }else{
                    tvISEmpty.visibility=View.VISIBLE
                }

                this@MainFollowPatient.dismissProgress()

            }


        })
        rvWeek!!.layoutManager = LinearLayoutManager(this)
        weekAdapter = WeeklyScheduleFollowPatientAdapter(context = this@MainFollowPatient, weekArray = listWeek!!.trainingWeeks)
        rvWeek!!.adapter = weekAdapter
        weekAdapter!!.notifyDataSetChanged()
    }
}
