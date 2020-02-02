package com.moose.githublite.fragments.repos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.moose.githublite.R
import com.moose.githublite.adapters.RepoListAdapter
import com.moose.githublite.model.GithubRepos
import com.moose.githublite.ui.MainActivity
import com.moose.githublite.ui.Splash
import kotlinx.android.synthetic.main.fragment_repos.*


class ReposFragment : Fragment(){

    private lateinit var reposViewModel: ReposViewModel
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var shared:SharedPreferences
    private var repos = ArrayList<GithubRepos>()
    private var token:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        shared = activity?.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        token = shared.getString("token","token")!!
        Log.d("token_", token)
        reposViewModel = ViewModelProviders.of(this).get(ReposViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repos, container, false)
        swipeRefreshLayout = view.findViewById(R.id.content)
        val connectionObserver = Observer<String> {
            if (it == "No connection"){
                swipeRefreshLayout.isRefreshing = false
                view.findViewById<RelativeLayout>(R.id.connection_error).visibility = View.VISIBLE
                progress_bar.visibility = View.GONE
            }
        }
        val reposObserver = Observer<List<GithubRepos>>{
            for (item in it){
                if (!item.fork){
                    repos.add(item)
                    Log.d("item_", repos.toString())
                }
            }
            view.findViewById<RelativeLayout>(R.id.connection_error).visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
            setRecyclerView(view)
        }
        swipeRefreshLayout.setOnRefreshListener{
            reposViewModel.connection.observe(this, connectionObserver)
            reposViewModel.repos.observe(this, reposObserver)
            reposViewModel.getRepos(token)
        }
        reposViewModel.connection.observe(this, connectionObserver)
        reposViewModel.repos.observe(this, reposObserver)
        reposViewModel.getRepos(token)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout -> {logout()
                true}
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun logout(){
        shared.edit()
            .putBoolean("loggedIn", false)
            .apply().run{
                val intent = Intent(requireContext(), Splash::class.java)
                startActivity(intent)
                activity!!.finish()
            }
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