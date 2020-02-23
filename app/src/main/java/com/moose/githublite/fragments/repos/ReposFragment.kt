package com.moose.githublite.fragments.repos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.moose.githublite.R
import com.moose.githublite.adapters.RepoListAdapter
import com.moose.githublite.model.GithubRepos
import com.moose.githublite.ui.Splash
import kotlinx.android.synthetic.main.fragment_repos.*


class ReposFragment : Fragment(){

    private lateinit var reposViewModel: ReposViewModel
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var shared:SharedPreferences
    private var repos = ArrayList<GithubRepos>()
    private var token:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this.requireContext(), getString(R.string.ad_app_id))
        setHasOptionsMenu(true)
        shared = activity?.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        token = shared.getString("token","token")!!
        Log.d("token_", token)
        reposViewModel = ViewModelProviders.of(this).get(ReposViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repos, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Ads
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val connectionObserver = Observer<String> {
            if (it == "No connection"){
                content.isRefreshing = false
                connection_error.visibility = View.VISIBLE
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
            connection_error.visibility = View.GONE
            content.isRefreshing = false
            setRecyclerView(view)
        }
        content.setOnRefreshListener{
            reposViewModel.connection.observe(this, connectionObserver)
            reposViewModel.repos.observe(this, reposObserver)
            reposViewModel.getRepos(token)
        }
        reposViewModel.connection.observe(this, connectionObserver)
        reposViewModel.repos.observe(this, reposObserver)
        reposViewModel.getRepos(token)
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
        progress_bar.visibility = View.INVISIBLE
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