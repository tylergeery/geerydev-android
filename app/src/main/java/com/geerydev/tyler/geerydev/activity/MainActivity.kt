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
                setContent(PostListFragment(), TAG_POST_LIST)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_projects -> {
                setContent(ProjectListFragment(), TAG_PROJECT_LIST)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                setContent(AboutFragment(), TAG_ABOUT)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(TAG_POST_LIST)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PostListFragment(), TAG_POST_LIST)
                    .commit()
        }
    }

    fun setContent(frag: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, frag, tag)
                .addToBackStack(null)
                .commit()
    }

    fun setChecked(index: Int) {
        navigation.selectedItemId = index
    }

    companion object {
        val TAG_POST_LIST = "POST_LIST"
        val TAG_POST_CONTENT = "POST_CONTENT"
        val TAG_PROJECT_LIST = "PROJECT_LIST"
        val TAG_ABOUT = "ABOUT"
    }
}