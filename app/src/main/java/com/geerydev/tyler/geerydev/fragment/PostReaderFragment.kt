package com.geerydev.tyler.geerydev.fragment

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.activity.MainActivity
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
        println("Creating " + this.javaClass.toString() + ": " + id)

        getPost()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println(this.javaClass.toString() + ": Creating post content view")
        return inflater.inflate(R.layout.post_content, container, false)
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
        println(this.javaClass.toString() + " Found post: " + post.question)
        activity?.actionBar?.setTitle(post.question)
//        activity.supportActionBar?.setTitle(post.question)

        val webView = view?.findViewById(R.id.post_reader_content) as WebView?

        if (webView != null) {
            val encoded = Base64.encodeToString(post.response.toByteArray(), Base64.NO_PADDING)

            webView.loadData(encoded,"text/html", "base64")
        }
        println(this.javaClass.toString() + " webview exists: " + (webView != null).toString())
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