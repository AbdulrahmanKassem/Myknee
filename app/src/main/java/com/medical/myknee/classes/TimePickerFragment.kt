package com.medical.myknee.classes

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker
import com.medical.myknee.Interface.GetTime
import java.util.*

@SuppressLint("ValidFragment")
class TimePickerFragment(var i: Int) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    var time:GetTime?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute,
                DateFormat.is24HourFormat(activity)
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        var calendar = Calendar.getInstance()
        if (i==1) {
            time!!.timeEveningExercise(year = calendar.get(Calendar.YEAR),month =calendar.get(Calendar.MONTH) ,
                    day =calendar.get(Calendar.DAY_OF_MONTH) ,hour =hourOfDay ,minute =minute )
        }else{
            time!!.timemorningExercise(year = calendar.get(Calendar.YEAR),month =calendar.get(Calendar.MONTH) ,
                    day =calendar.get(Calendar.DAY_OF_MONTH) ,hour =hourOfDay ,minute =minute )

        }

    }
}