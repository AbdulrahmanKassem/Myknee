package com.medical.myknee.specialized

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.medical.myknee.adapter.SelectExerciseAdapter
import com.medical.myknee.R
import kotlinx.android.synthetic.main.activity_select_exercise.*

class SelectExercise : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_exercise)
        tvNameWeek.text=this.intent.getStringExtra("weekName")
        var exerciseArray = ArrayList<Int>()
        exerciseArray.add(R.drawable.exercise1)
        exerciseArray.add(R.drawable.exercise2)
        exerciseArray.add(R.drawable.exercise3)
        exerciseArray.add(R.drawable.exercise4)
        exerciseArray.add(R.drawable.exercise5)
        exerciseArray.add(R.drawable.exercise6)
        exerciseArray.add(R.drawable.exercise7)
        exerciseArray.add(R.drawable.exercise8)
        exerciseArray.add(R.drawable.exercise9)
        rvExercize!!.layoutManager = LinearLayoutManager(this)
        var weekAdapter=SelectExerciseAdapter(context = this, imageExercise = exerciseArray,weekSelected = intent.getIntExtra("position",0))
        rvExercize!!.adapter = weekAdapter

        btnNextToTimer.setOnClickListener {
            var intent=Intent(this, SelectExerciseTime::class.java)
            intent.putExtra("position",this.intent.getIntExtra("position",0))

            intent.putExtra("uid",this@SelectExercise.intent.getStringExtra("uid"))
            startActivity(intent)
            this.finish()
        }
    }

}
