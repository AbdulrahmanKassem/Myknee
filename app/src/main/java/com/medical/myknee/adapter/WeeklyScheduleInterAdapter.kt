package com.medical.myknee.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.medical.myknee.intern.ReadyToExercise
import com.medical.myknee.model.Week
import com.medical.myknee.R

class WeeklyScheduleInterAdapter (val context: Context,
                                  val weekArray: ArrayList<Week>?)
    : RecyclerView.Adapter<WeeklyScheduleInterAdapter.MainViewHolder>() {
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.tvNameWeekList!!.text = weekArray!![position].weekName

        holder.linearLayout!!.setOnClickListener {
            if ( weekArray[position].trainingId!!.isNotEmpty()) {
                var intent = Intent(this.context, ReadyToExercise::class.java)
                intent.putExtra("position", position)
                intent.putExtra("weekArray", weekArray)
                intent.putExtra("timer", weekArray[position].days!![0].timeOfExerciseInDay!!.timer)
                intent.putExtra("arrayTraining", weekArray[position].trainingId)
                context.startActivity(intent)
            }else{
                Toast.makeText(this.context, "لا توجد تمارين لهذا الاسبوع", Toast.LENGTH_LONG).show()

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyScheduleInterAdapter.MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_week, parent, false))
    }


    override fun getItemCount(): Int = this.weekArray!!.size

    open class MainViewHolder constructor(itemView: View?)
        : RecyclerView.ViewHolder(itemView) {

        var tvNameWeekList: TextView? = null
        var linearLayout: LinearLayout? = null

        init {
            tvNameWeekList = itemView!!.findViewById(R.id.tvNameWeekList)
            linearLayout = itemView.findViewById(R.id.linearLayout)
        }
    }
}