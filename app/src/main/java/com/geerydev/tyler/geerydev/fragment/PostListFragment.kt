package com.geerydev.tyler.geerydev.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.geerydev.tyler.geerydev.ui.post.PostAdapter
import com.geerydev.tyler.geerydev.model.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.geerydev.tyler.geerydev.R
import kotlinx.android.synthetic.main.fragment_post_summary_list.*


class PostListFragment : BaseFragment() {

    private var page: Int = 1
    private var per_page: Int = 10
    private var sort: String = "created"

    private var disposable: Disposable? = null

    private lateinit var viewAdapter: PostAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var forward_button: Button
    private lateinit var back_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = PostAdapter(this)

        getPosts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_post_summary_list, container, false)
        val view = root.findViewById(R.id.psl_recycler) as RecyclerView

        view.apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forward_button = activity!!.findViewById(R.id.page_forward)
        back_button = activity!!.findViewById(R.id.page_back)

        forward_button.text = ">"
        back_button.text = "<"

        forward_button.setOnClickListener {
            page++

            getPosts()
        }

        back_button.setOnClickListener {
            if (page > 1) {
                page--

                getPosts()
            }
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

        println("Per Page: " + per_page.toString() + " " + (result.count() < per_page).toString())
//        if (result.count() < per_page) {
//            forward_button.visibility = View.GONE
//        } else {
//            forward_button.visibility = View.VISIBLE
//        }
//
//        if (page > 1) {
//            back_button.visibility = View.VISIBLE
//        } else {
//            back_button.visibility = View.GONE
//        }
    }
}
