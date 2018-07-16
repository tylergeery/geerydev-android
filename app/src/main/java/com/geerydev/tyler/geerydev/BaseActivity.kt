package com.geerydev.tyler.geerydev

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {

    protected val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_posts -> {
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_projects -> {
                message.setText(R.string.title_projects)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                message.setText(R.string.title_about)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}