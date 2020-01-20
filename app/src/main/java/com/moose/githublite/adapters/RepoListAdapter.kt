package com.moose.githublite.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moose.githublite.R
import com.moose.githublite.model.GithubRepos
import net.cachapa.expandablelayout.ExpandableLayout

class RepoListAdapter(val repos: List<GithubRepos>) : RecyclerView.Adapter<RepoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        return holder.bind(repos[position])
    }
}

class RepoViewHolder(view: View) :RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.name)
    private val descrition: TextView = view.findViewById(R.id.description)
    private val date:TextView = view.findViewById(R.id.date)
    private val stars:TextView = view.findViewById(R.id.stars)
    private val license:TextView = view.findViewById(R.id.license)
    private val expandingView:ExpandableLayout = view.findViewById(R.id.expandable_layout)

    @SuppressLint("SetTextI18n")
    fun bind(repos: GithubRepos) {
        title.text = repos.name
        descrition.text = repos.description
        stars.text = "Stars : ${repos.stargazers_count}"
        date.text = "Created: ${repos.created_at.split("T")[0]}"
        title.setOnClickListener {
            expandingView.toggle()
        }
        expandingView.setOnClickListener {
            expandingView.toggle()
        }
        if (repos.license != null)
            license.text = "License : ${repos.license.name}"
        else
            license.text = "License : null"
    }

}
