package com.medical.myknee.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.medical.myknee.model.Week
import com.medical.myknee.R
import com.medical.myknee.specialized.SelectExercise

class WeeksAdapter(val context: Context,
                   val weekArray: ArrayList<Week>?,var uid:String)
    : RecyclerView.Adapter<WeeksAdapter.MainViewHolder>() {
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.tvNameWeekList!!.text = weekArray!![position].weekName

        holder.linearLayout!!.setOnClickListener {
            var intent = Intent(this.context, SelectExercise::class.java)
            Log.d("test","uid   3"+uid)

            intent.putExtra("weekName", weekArray[position].weekName)
            intent.putExtra("uid",uid)
            intent.putExtra("position", position)
            context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeksAdapter.MainViewHolder {
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