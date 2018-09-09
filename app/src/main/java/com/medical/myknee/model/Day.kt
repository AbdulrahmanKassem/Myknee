package com.medical.myknee.model

import java.io.Serializable

class Day : Serializable {
    var dayName:String?=null
    var timeOfExerciseInDay: TimeOfExercise? = TimeOfExercise()
    var isDo:Boolean=false
}