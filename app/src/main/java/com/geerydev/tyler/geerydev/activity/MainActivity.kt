package com.geerydev.tyler.geerydev.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.fragment.AboutFragment
import com.geerydev.tyler.geerydev.fragment.PostListFragment
import com.geerydev.tyler.geerydev.fragment.ProjectListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    protected val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_posts -> {
                setContent(PostListFragment())

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_projects -> {
                setContent(ProjectListFragment())

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                setContent(AboutFragment())

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState != null) {
            return;
        }

        setContent(PostListFragment())
    }

    fun setContent(frag: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, frag)
                .commit()
    }

    fun setChecked(index: Int) {
        navigation.selectedItemId = index
    }
}