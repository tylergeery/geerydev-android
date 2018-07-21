package com.geerydev.tyler.geerydev.activity

import android.os.Bundle
import com.geerydev.tyler.geerydev.activity.BaseActivity

class ProjectActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getProjects()
    }

    override fun onResume() {
        super.onResume()
        getProjects()
    }

    override fun onPause() {
        super.onPause()
    }

    fun getProjects() {

    }
}