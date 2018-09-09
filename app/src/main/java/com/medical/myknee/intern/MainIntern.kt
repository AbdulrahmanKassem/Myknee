package com.medical.myknee.intern

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.medical.myknee.fragments.ExercisesFragment
import com.medical.myknee.fragments.ProfileFragment
import com.medical.myknee.R
import com.medical.myknee.classes.BaseActivity
import com.medical.myknee.SignInActivity
import kotlinx.android.synthetic.main.activity_main_intern.*
class MainIntern : BaseActivity() {

    private val exercisesFragment = ExercisesFragment.instance
    private val profileFragment = ProfileFragment.instance
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.exercises -> {
                if (!exercisesFragment.isAdded) {
                    this.commitFT(this.exercisesFragment)
                    return@OnNavigationItemSelectedListener true
                }
                return@OnNavigationItemSelectedListener false
            }
            R.id.profile -> {
                if (!profileFragment.isAdded) {
                    this.commitFT(this.profileFragment)
                    return@OnNavigationItemSelectedListener true
                }
                return@OnNavigationItemSelectedListener false
            }
        }
        false
    }

    private fun commitFT(fragment: Fragment) {

        val fragmentTransaction = this.fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.content, fragment)
        fragmentTransaction.disallowAddToBackStack()
        fragmentTransaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_intern)
        navigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId= R.id.exercises



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {

            R.id.logOut -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainIntern, SignInActivity::class.java))
                this@MainIntern.finish()
                return true}

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.specialized_menu, menu)
        return super.onCreateOptionsMenu(menu)


    }
}
