package com.geerydev.tyler.geerydev.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.geerydev.tyler.geerydev.activity.BaseActivity

class PostReaderActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(INTENT_POST_ID)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {

        private val INTENT_POST_ID = "POST_ID"

        fun newIntent(context: Context, postId: String): Intent {
            val intent = Intent(context, PostReaderActivity::class.java)
            intent.putExtra(INTENT_POST_ID, postId)

            return intent
        }
    }
}