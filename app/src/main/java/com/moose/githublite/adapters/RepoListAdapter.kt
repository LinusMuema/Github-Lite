package com.moose.githublite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moose.githublite.R
import com.moose.githublite.model.GithubRepos

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

    fun bind(repos: GithubRepos) {
        title.text = repos.name
        descrition.text = repos.description
    }

}
