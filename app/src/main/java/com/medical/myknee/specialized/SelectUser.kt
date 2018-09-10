@file:Suppress("UNREACHABLE_CODE")

package com.medical.myknee.specialized

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.medical.myknee.model.User
import com.medical.myknee.R
import com.medical.myknee.classes.BaseActivity
import com.medical.myknee.SignInActivity
import com.medical.myknee.specialized.followPatient.MainFollowPatient
import kotlinx.android.synthetic.main.activity_select_user.*

class SelectUser : BaseActivity(), View.OnClickListener {


    private var mAuth: FirebaseAuth? = null
    private var mDatabase: DatabaseReference? = null
    private var arrayUseer: ArrayList<User>? = ArrayList()
    private var arrayNameUsers = ArrayList<String>()
    private var x = -1
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_select_user)
        btnSave.setOnClickListener(this)
        btnAddSchedule.setOnClickListener(this)
        followPatient.setOnClickListener(this)
        arrayNameUsers.add("اختر المستخدم")
        this.showprogress()
        mDatabase = FirebaseDatabase.getInstance().reference.child("users")
        mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in dataSnapshot.children) {
                    val userx: User = dataSnapshot.getValue(User::class.java)!!
                    if (mAuth!!.uid.toString() != userx.uid) {
                        arrayUseer!!.add(userx)
                        arrayNameUsers.add(userx.name!!)
                    }
                }
                this@SelectUser.dismissProgress()
            }

        })


        spinnerUsers.gravity = 17
        var arrayAdapter=ArrayAdapter(this, R.layout.spinner_textview_user, arrayNameUsers)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_textview_user)
        spinnerUsers.adapter = arrayAdapter



        spinnerUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (arrayUseer!!.isNotEmpty()) {
                    if (arrayUseer!![p2 + x].admin) {
                        user = arrayUseer!![p2 + x]
                        tvName.text=user!!.name
                        tvEmail.text=user!!.email
                        tvPhone.text=user!!.phone
                        tvGender.text=user!!.gender
                        radioSpecialized.isChecked = true
                    } else {
                        user = arrayUseer!![p2 + x]
                        tvName.text=user!!.name
                        tvEmail.text=user!!.email
                        tvPhone.text=user!!.phone
                        tvGender.text=user!!.gender
                        radioIntern.isChecked = true
                    }
                    if (x != 0) {
                        arrayNameUsers.remove("اختر المستخدم")
                        x = 0
                    }
                }

            }

        }

    }

    override fun onClick(v: View?) {
        when (v) {
            btnSave -> {
                if (user!=null) {
                    user!!.admin = radioSpecialized.isChecked
                    mDatabase!!.child(user!!.uid.toString()).child("admin").setValue(user!!.admin)
                }else{
                    this.showLongToast("الرجاء تحديد المستخدم")
                }
            }
            btnAddSchedule -> {
                if (user!=null) {
                if (!user!!.admin) {
                    var intent = Intent(v!!.context, MainSpecialized::class.java)
                    Log.d("test", "uid   1" + this@SelectUser.intent.getStringExtra("uid"))
                    intent.putExtra("uid", user!!.uid)
                    v.context.startActivity(intent)
                } else {
                    this@SelectUser.showLongToast("لا يمكن وضع جدول لاخصائي")
                }
            }else {
                    this.showLongToast("الرجاء تحديد المستخدم")
                }
            }
            followPatient ->{
                if (user!=null) {
                var intent = Intent(v!!.context, MainFollowPatient::class.java)
                intent.putExtra("userUid", user!!.uid.toString())
                v.context.startActivity(intent)
                }else{
                    this.showLongToast("الرجاء تحديد المستخدم")
                }
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {

            R.id.logOut -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@SelectUser, SignInActivity::class.java))
                this@SelectUser.finish()
                return true}

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.specialized_menu, menu)
        return super.onCreateOptionsMenu(menu)


    }
}
