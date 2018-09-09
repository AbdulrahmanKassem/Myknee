package com.medical.myknee.model

import java.io.Serializable

class User : Serializable {
    var name: String?=null
    var password: String?=null
    var email: String?=null
    var phone: String?=null
    var gender: String? = null
    var uid: String? = null
    var admin: Boolean = false

}