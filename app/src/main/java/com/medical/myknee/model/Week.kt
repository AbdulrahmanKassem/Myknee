package com.medical.myknee.model

import java.io.Serializable

class Week : Serializable {
    var weekName :String?=null
    var days :ArrayList<Day>?=ArrayList()
    var trainingId :ArrayList<Int>?=ArrayList()
}