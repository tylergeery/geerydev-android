package com.geerydev.tyler.geerydev.fragment

import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.model.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers



class PostReaderFragment(): BaseFragment() {

    private lateinit var id: String
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments

        id = if (args != null) args.getString(INTENT_POST_ID) else ""
        println("Creating PostReaderActivity: " + id)

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

        fun newInstance(postId: String): PostReaderFragment {
            val fragment = PostReaderFragment()
            val bundle = Bundle(1)

            bundle.putString(INTENT_POST_ID, postId)
            fragment.setArguments(bundle)

            return fragment
        }
    }

    fun displayPost(post: Post) {
        println("PostReaderActivity Found post: " + post.question)
        activity?.actionBar?.setTitle(post.question)
//        activity.supportActionBar?.setTitle(post.question)

        val webView = activity?.findViewById(R.id.post_reader_content) as WebView
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