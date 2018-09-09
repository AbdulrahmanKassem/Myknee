package com.medical.myknee.model

import java.io.Serializable

class TimeOfExercise: Serializable {
    var exerciseEvening:ExerciseDate?=null
    var exerciseMorning:ExerciseDate?=null
    var timer:Int?=10

}