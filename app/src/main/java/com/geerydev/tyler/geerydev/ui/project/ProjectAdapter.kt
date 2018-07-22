package com.geerydev.tyler.geerydev.ui.project

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.activity.ProjectActivity
import com.geerydev.tyler.geerydev.model.Project
import java.util.*

class ProjectAdapter(val projectActivity: ProjectActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var projects: List<Project> = ArrayList()

    inner class ProjectViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun setOnClickListener() {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            println("ProjectAdapter ViewHolder Clicked: " + adapterPosition)
            println(this@ProjectAdapter.projects[adapterPosition])

        }
    }

    fun setProjectList(projectList: List<Project>) {
        projects = projectList

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return projects.count()
    }

    override fun getItemId(position: Int): Long = 0L

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ProjectViewHolder {
        // create a new view
        // set the view's size, margins, paddings and layout parameters
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.project, parent, false)

        return ProjectViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val project: Project = projects[position]
        val titleView = holder.itemView.findViewById(R.id.project_title) as TextView
        val detailsView = holder.itemView.findViewById(R.id.project_details) as TextView

        titleView.text = project.title
        detailsView.text = project.detail

        (holder as ProjectViewHolder).setOnClickListener()
    }
}