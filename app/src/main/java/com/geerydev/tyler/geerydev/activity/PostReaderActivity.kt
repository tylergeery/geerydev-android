package com.geerydev.tyler.geerydev.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.model.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PostReaderActivity: BaseActivity() {

    private lateinit var id: String
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getStringExtra(INTENT_POST_ID)

        println("Creating PostReaderActivity: " + id)
        setContentView(R.layout.post_content)

        getPost()
    }

    override fun onResume() {
        super.onResume()
        getPost()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    companion object {

        private val INTENT_POST_ID = "POST_ID"

        fun newIntent(context: Context, postId: String): Intent {
            val intent = Intent(context, PostReaderActivity::class.java)
            intent.putExtra(INTENT_POST_ID, postId)
            println("PostReaderActivity starting: " + postId)

            return intent
        }
    }

    fun displayPost(post: Post) {
        println("PostReaderActivity Found post: " + post.question)
        actionBar?.setTitle(post.question)
        supportActionBar?.setTitle(post.question)

        val webView = findViewById(R.id.post_reader_content) as WebView
        val encoded = Base64.encodeToString(post.response.toByteArray(), Base64.NO_PADDING)

        webView.loadData(encoded,"text/html", "base64")
    }

    fun getPost() {
        disposable =
                GeeryDevPostServe.fetchPost(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> displayPost(result) },
                                { error -> showError(error.message) }
                        )
    }
}