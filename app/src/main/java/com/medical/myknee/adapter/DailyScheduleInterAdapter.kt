package com.medical.myknee.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.medical.myknee.R
import com.medical.myknee.model.Day

class DailyScheduleInterAdapter(val context: Context,
                                val daysArray: ArrayList<Day>?, val weekName: String)
    : RecyclerView.Adapter<DailyScheduleInterAdapter.MainViewHolder>() {
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        var tvNameWeekExercise = (context as Activity).findViewById<TextView>(R.id.tvNameWeekExercise)
        var tvName = (context as Activity).findViewById<TextView>(R.id.tvNameDay)
        var tvDate = (context as Activity).findViewById<TextView>(R.id.tvDate)
        var tvExerciseMorning = (context as Activity).findViewById<TextView>(R.id.tvExerciseMorning)
        var tvExerciseEvening = (context as Activity).findViewById<TextView>(R.id.tvExerciseEvening)
        var ivDoExercise = (context as Activity).findViewById<ImageView>(R.id.ivDoExercise)
        holder.tvNameWeekList!!.text = daysArray!![position].dayName
        holder.imShowDetails!!.setImageResource(R.mipmap.ic_show_details)
        if (position == 0) {
            tvNameWeekExercise.text = weekName
            tvName.text = daysArray[position].dayName
            if (daysArray[position].timeOfExerciseInDay!!.exerciseMorning!=null){
                tvExerciseMorning.text = "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.hour} : " +
                        "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.minute}"

                tvDate.text = "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.year} / " +
                        "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.month} /" +
                        "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.day} "
            }else{
                tvExerciseMorning.text ="-"
            }

            if (daysArray[position].timeOfExerciseInDay!!.exerciseEvening!=null){

                tvExerciseEvening.text = "${daysArray[position].timeOfExerciseInDay!!.exerciseEvening!!.hour} : " +
                        "${daysArray[position].timeOfExerciseInDay!!.exerciseEvening!!.minute}"
                tvDate.text = "${daysArray[position].timeOfExerciseInDay!!.exerciseEvening!!.year} / " +
                        "${daysArray[position].timeOfExerciseInDay!!.exerciseEvening!!.month} /" +
                        "${daysArray[position].timeOfExerciseInDay!!.exerciseEvening!!.day} "
            }else{
                tvExerciseEvening.text ="-"
            }

            //   ivDoExercise.text=weekName

            if (daysArray[position].isDo) {
                ivDoExercise.setImageResource(R.mipmap.ic_check)
            } else {
                ivDoExercise.setImageResource(R.mipmap.ic_not_check)

            }
        }



        holder.linearLayout!!.setOnClickListener {
            tvNameWeekExercise.text = weekName
            tvName.text = daysArray[position].dayName
            tvExerciseMorning.text = "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.hour} : " +
                    "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.minute}"
            tvExerciseEvening.text = "${daysArray[position].timeOfExerciseInDay!!.exerciseEvening!!.hour} : " +
                    "${daysArray[position].timeOfExerciseInDay!!.exerciseEvening!!.minute}"
            //   ivDoExercise.text=weekName
            tvDate.text = "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.year} / " +
                    "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.month} /" +
                    "${daysArray[position].timeOfExerciseInDay!!.exerciseMorning!!.day} "

            if (daysArray[position].isDo) {
                ivDoExercise.setImageResource(R.mipmap.ic_check)
            } else {
                ivDoExercise.setImageResource(R.mipmap.ic_not_check)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyScheduleInterAdapter.MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_week, parent, false))
    }


    override fun getItemCount(): Int = this.daysArray!!.size

    open class MainViewHolder constructor(itemView: View?)
        : RecyclerView.ViewHolder(itemView) {

        var imShowDetails: ImageView? = null
        var tvNameWeekList: TextView? = null
        var linearLayout: LinearLayout? = null

        init {
            imShowDetails = itemView!!.findViewById(R.id.imShowDetails)
            tvNameWeekList = itemView.findViewById(R.id.tvNameWeekList)
            linearLayout = itemView.findViewById(R.id.linearLayout)
        }
    }
}