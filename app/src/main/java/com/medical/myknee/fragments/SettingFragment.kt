package com.medical.myknee.fragments

import android.app.Fragment
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.medical.myknee.R
import com.medical.myknee.SignInActivity
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {
    object Holder {
        val instance = SettingFragment()
    }

    companion object {
        val instance by lazy { Holder.instance }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this.context,SignInActivity::class.java))}


    }
}
