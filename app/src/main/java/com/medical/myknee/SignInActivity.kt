package com.medical.myknee

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.acticity_sign_in.*
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.database.*
import com.medical.myknee.intern.MainIntern
import com.medical.myknee.model.User
import com.medical.myknee.specialized.SelectUser
import com.medical.myknee.classes.BaseActivity


class SignInActivity : BaseActivity(), View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_sign_in)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        mAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
        btnForgetPassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSignUp -> {
                v.context.startActivity(Intent(v.context, SignUpActivity::class.java))
            }
            R.id.btnSignIn -> {
                if (etEmail.text.toString().trim().isNotEmpty()) {
                    if (etPassword.text.toString().trim().isNotEmpty()) {
                        this.showprogress()
                        SignIn(email = etEmail.text.toString().trim(),password = etPassword.text.toString().trim())
                    }else{
                        etPassword.error="الرجاء كتابه كلمه المرور"
                    }                }else{
                    etEmail.error="الرجاء كتابه الايميل"
                }
            }

            R.id.btnForgetPassword -> {
            }
        }
    }

    fun SignIn(email:String,password:String){
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        mAuth = FirebaseAuth.getInstance()
                        var currentUser = mAuth!!.currentUser
                        mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(currentUser!!.uid)
                        mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(databaseError: DatabaseError) {

                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val user:User= dataSnapshot.getValue(User::class.java)!!
                                Log.d("test", user.admin.toString())
                                this@SignInActivity.dismissProgress()

                                if(user.admin) {
                                    startActivity(Intent(this@SignInActivity,SelectUser::class.java))
                                }else{
                                    startActivity(Intent(this@SignInActivity,MainIntern::class.java))

                                }
                                this@SignInActivity.finish()


                            }


                        })
                    } else {
                        this.dismissProgress()
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@SignInActivity, "خطاء في تسجيل الدخول",
                                Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }

    override fun onStart() {
        super.onStart()
        var currentUser = mAuth!!.currentUser
        if (currentUser!=null){
            this.showprogress()
            mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(currentUser.uid)
            mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                  val user:User= dataSnapshot.getValue(User::class.java)!!
                    Log.d("test", user.admin.toString())
                    this@SignInActivity.dismissProgress()

                    if(user.admin) {
                        startActivity(Intent(this@SignInActivity,SelectUser::class.java))
                    }else{
                        startActivity(Intent(this@SignInActivity,MainIntern::class.java))

                    }
                    this@SignInActivity.finish()


                }


            })
        }
    }
}
