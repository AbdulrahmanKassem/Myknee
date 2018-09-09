package com.medical.myknee.classes

import java.util.regex.Pattern

object Tools {

    fun isSaudiNumber(userMobile: String): Boolean {
        val mobileLength = userMobile.length
        if (mobileLength == 10) {
            return userMobile.startsWith("05")
        } else if (mobileLength == 9) {
            return userMobile.startsWith("5")
        } else if (mobileLength == 12) {
            return userMobile.startsWith("9665")
        }
        return false
    }

    fun validateEmail(email: String): Boolean {
        return Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                .matcher(email)
                .matches()
    }

    fun validatePassword(passWord: String): Boolean {
        return  (passWord.length>4)
    }

}