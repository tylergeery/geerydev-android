package com.geerydev.tyler.geerydev.fragment

import android.support.v4.app.Fragment
import android.widget.Toast
import com.geerydev.tyler.geerydev.network.GeeryDevService

open class BaseFragment: Fragment() {
    protected val GeeryDevPostServe by lazy {
        GeeryDevService.create()
    }

    protected fun showError(message: String?) {
        println("Fragment Error: " + message)
        Toast.makeText(context, "Fragment Error: " + message, Toast.LENGTH_SHORT).show()
    }
}