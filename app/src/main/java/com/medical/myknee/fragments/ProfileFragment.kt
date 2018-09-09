package com.medical.myknee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.medical.myknee.classes.BaseFragment
import com.medical.myknee.model.User
import com.medical.myknee.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment() {
    object Holder {
        val instance = ProfileFragment()
    }

    companion object {
        val instance by lazy { Holder.instance }
    }

    private var mAuth: FirebaseAuth? = null
    private var mDatabase: DatabaseReference? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        if (user == null) {
            this@ProfileFragment.showprogress()
            mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(mAuth!!.uid.toString())
            mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    this@ProfileFragment.dismissProgress()
                    user = dataSnapshot.getValue(User::class.java)!!
                    tvMyEmail.text=user!!.email
                    tvMyGender.text=user!!.gender
                    tvMyName.text=user!!.name
                    tvMyPhone.text=user!!.phone

                }
            })
        }else{
            tvMyEmail.text=user!!.email
            tvMyGender.text=user!!.gender
            tvMyName.text=user!!.name
            tvMyPhone.text=user!!.phone
        }
    }
}
