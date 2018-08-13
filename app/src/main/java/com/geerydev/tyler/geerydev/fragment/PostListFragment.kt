package com.geerydev.tyler.geerydev.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geerydev.tyler.geerydev.ui.post.PostAdapter
import com.geerydev.tyler.geerydev.model.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.geerydev.tyler.geerydev.R


class PostListFragment : BaseFragment() {

    private var page: Int = 1
    private var per_page: Int = 10
    private var sort: String = "created"

    private var disposable: Disposable? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PostAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = PostAdapter(this)


        getPosts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val resp = super.onCreateView(inflater, container, savedInstanceState)

        return resp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        getPosts()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun getPosts() {
        disposable =
                GeeryDevPostServe.fetchPosts(sort, "response", page, per_page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> showResult(result) },
                                { error -> showError(error.message) }
                        )
    }

    private fun showResult(result: List<Post>) {
        println("Result: " + result.count())

        viewAdapter.setPostViews(result)
    }
}
