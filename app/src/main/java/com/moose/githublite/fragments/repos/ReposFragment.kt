package com.moose.githublite.fragments.repos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moose.githublite.R
import com.moose.githublite.model.GithubRepos

class ReposFragment : Fragment() {

    private lateinit var reposViewModel: ReposViewModel
    private lateinit var shared:SharedPreferences
    private lateinit var repos:List<GithubRepos>
    private var token:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = activity?.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        token = shared.getString("token","token")!!
        reposViewModel = ViewModelProviders.of(this).get(ReposViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repos, container, false)
        val reposObserver = Observer<List<GithubRepos>>{
            repos = it
            setRecyclerView()
        }
        reposViewModel.repos.observe(this, reposObserver)
        reposViewModel.getRepos(token)
        return view
    }

    private fun setRecyclerView() {

    }
}