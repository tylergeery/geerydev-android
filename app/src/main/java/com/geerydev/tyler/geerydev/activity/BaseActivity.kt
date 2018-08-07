package com.geerydev.tyler.geerydev.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.network.GeeryDevService
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {

    protected val GeeryDevPostServe by lazy {
        GeeryDevService.create()
    }

    protected val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_posts -> {
                val intent = Intent(this, PostActivity::class.java)

                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_projects -> {
                val intent = Intent(this, ProjectActivity::class.java)

                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                val intent = Intent(this, AboutActivity::class.java)

                startActivity(intent)
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

    protected fun setChecked(index: Int) {
        navigation.selectedItemId = index
    }

    protected fun showError(message: String?) {
        println("Error: " + message)
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show()
    }
}