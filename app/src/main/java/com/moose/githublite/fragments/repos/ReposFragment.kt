package com.moose.githublite.fragments.repos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moose.githublite.R
import com.moose.githublite.adapters.RepoListAdapter
import com.moose.githublite.model.GithubRepos
import kotlinx.android.synthetic.main.fragment_repos.*

class ReposFragment : Fragment() {

    private lateinit var reposViewModel: ReposViewModel
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var shared:SharedPreferences
    private var repos = ArrayList<GithubRepos>()
    private var token:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = activity?.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        token = shared.getString("token","token")!!
        Log.d("token_", token)
        reposViewModel = ViewModelProviders.of(this).get(ReposViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repos, container, false)
        val reposObserver = Observer<List<GithubRepos>>{
            for (item in it){
                if (!item.fork){
                    repos.add(item)
                    Log.d("item_", repos.toString())
                }
            }
            setRecyclerView(view)
        }
        reposViewModel.repos.observe(this, reposObserver)
        reposViewModel.getRepos(token)
        return view
    }

    private fun setRecyclerView(view: View) {
        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.INVISIBLE
        viewManager = LinearLayoutManager(this.requireContext())
        viewAdapter = RepoListAdapter(repos)
        Log.d("repos_", repos.toString())
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}