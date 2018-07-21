package com.geerydev.tyler.geerydev.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.model.Project
import com.geerydev.tyler.geerydev.ui.project.ProjectAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProjectActivity: BaseActivity() {
    private var disposable: Disposable? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ProjectAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ProjectAdapter(this)

        recyclerView = findViewById<RecyclerView>(R.id.project_list_view).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        getProjects()
    }


    override fun onResume() {
        super.onResume()
        getProjects()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    fun getProjects() {
        disposable =
                GeeryDevPostServe.fetchProjects()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> showProjects(result) },
                                { error -> showError(error.message) }
                        )
    }

    fun showProjects(projectList: List<Project>) {
        viewAdapter.setProjectList(projectList)
    }
}