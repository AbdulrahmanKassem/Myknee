package com.medical.myknee.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.medical.myknee.specialized.MainSpecialized
import com.medical.myknee.R

class SelectExerciseAdapter (val context: Context,
                             val imageExercise: ArrayList<Int>,
                             val weekSelected:Int)
    : RecyclerView.Adapter<SelectExerciseAdapter.MainViewHolder>() {
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Glide.with(context)
                .load(imageExercise[position])
                .into(holder.gifExersize!!)

        holder.relativeLayout!!.setOnClickListener {
            if (holder.icCheck!!.visibility==View.GONE) {
                MainSpecialized.instance.trainingWeeks!![weekSelected].trainingId!!.add(position)
                holder.icCheck!!.visibility=View.VISIBLE
            }
            else {
                for (i in 0 until MainSpecialized.instance.trainingWeeks!![weekSelected].trainingId!!.size){
                    if (MainSpecialized.instance.trainingWeeks!![weekSelected].trainingId!![i]==imageExercise[position]){
                       MainSpecialized.instance.trainingWeeks!![weekSelected].trainingId!!.remove(i)
                        holder.icCheck!!.visibility=View.GONE

                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectExerciseAdapter.MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_exrcise, parent, false))
    }


    override fun getItemCount(): Int = this.imageExercise.size

    open class MainViewHolder constructor(itemView: View?)
        : RecyclerView.ViewHolder(itemView) {
        var gifExersize:ImageView?=null
        var icCheck:ImageView?=null
        var relativeLayout:RelativeLayout?=null



        init {
            gifExersize=itemView!!.findViewById(R.id.gifExersize)
            icCheck=itemView.findViewById(R.id.icCheck)
            relativeLayout=itemView.findViewById(R.id.relativeLayout)

        }
    }
}