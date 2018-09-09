package com.medical.myknee

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.medical.myknee.model.User
import com.medical.myknee.classes.Tools
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.Toast
import android.content.Intent
import android.view.WindowManager
import com.google.firebase.database.FirebaseDatabase
import com.medical.myknee.intern.MainIntern
import com.medical.myknee.classes.BaseActivity


class SignUpActivity : BaseActivity() {
    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        male.isChecked = true
        mAuth = FirebaseAuth.getInstance()

        btnCreateNewUser.setOnClickListener {
            SignUp()
        }
    }

    fun SignUp() {
        if (edName.text.toString().trim().isNotEmpty()) {
            if (edEmail.text.toString().trim().isNotEmpty() && Tools.validateEmail(edEmail.text.toString().trim())) {
                if (edPassword.text.toString().trim().isNotEmpty() && Tools.validatePassword(edPassword.text.toString().trim())) {
                    if (edPhoneNumber.text.toString().trim().isNotEmpty() && Tools.isSaudiNumber(edPhoneNumber.text.toString().trim())) {

                        var user = User()
                        user.name = edName.text.trim().toString()
                        user.email = edEmail.text.trim().toString()
                        user.password = edPassword.text.trim().toString()
                        user.phone = edPhoneNumber.text.trim().toString()
                        if (male.isChecked)
                            user.gender = "ذكر"
                        else
                            user.gender = "انثى"

                        this.showprogress()
                        createUser(user = user)

                    } else {
                        edPhoneNumber.error = this.getString(R.string.textError)
                    }
                } else {
                    edPassword.error = "يجب ان لا يقل عن خمسة ارقام"
                }
            } else {
                edEmail.error = this.getString(R.string.textError)
            }
        } else {
            edName.error = this.getString(R.string.textError)
        }

    }

    private fun createUser(user: User) {
        mAuth!!.createUserWithEmailAndPassword(user.email!!, user.password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        this.dismissProgress()
                         mAuth!!.currentUser
                        user.uid=mAuth!!.uid
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("users").child(mAuth!!.uid.toString())
                        myRef.setValue(user)
                        startActivity(Intent(this@SignUpActivity,MainIntern::class.java))
                    } else {
                        this.dismissProgress()
                        Toast.makeText(this@SignUpActivity, "خطاء في التسجيل ",
                                Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }
    }
}
