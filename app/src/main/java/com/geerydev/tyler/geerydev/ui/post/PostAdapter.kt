package com.geerydev.tyler.geerydev.ui.post

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.model.Post
import java.text.SimpleDateFormat
import java.util.*

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var blogPosts: List<Post> = ArrayList()

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    fun setPostViews(posts: List<Post>) {
        blogPosts = posts

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return blogPosts.count()
    }

    override fun getItemId(position: Int): Long = 0L

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PostViewHolder {
        // create a new view
        // set the view's size, margins, paddings and layout parameters
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout, parent, false)

        return PostViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val post: Post = blogPosts[position]
        val dateView = holder.itemView.findViewById(R.id.post_date) as TextView
        val titleView = holder.itemView.findViewById(R.id.post_title) as TextView

        // TODO: make human-readable date
        val format = SimpleDateFormat("MMM\nyyyy")
        dateView.text = format.format(post.created)
        titleView.text = post.question
    }
}