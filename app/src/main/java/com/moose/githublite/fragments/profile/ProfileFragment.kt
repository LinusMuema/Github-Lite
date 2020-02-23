package com.moose.githublite.fragments.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.moose.githublite.R
import com.moose.githublite.adapters.EventListAdapter
import com.moose.githublite.model.Event
import com.moose.githublite.model.GithubUser
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.adView
import kotlinx.android.synthetic.main.fragment_profile.connection_error
import kotlinx.android.synthetic.main.fragment_profile.content
import kotlinx.android.synthetic.main.fragment_profile.progress_bar
import kotlinx.android.synthetic.main.fragment_repos.*

class ProfileFragment : Fragment() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var shared:SharedPreferences
    private lateinit var user: GithubUser
    private lateinit var token:String
    private lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this.requireContext(), getString(R.string.ad_app_id))
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        appContext = activity!!.applicationContext
        shared = activity?.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        token = shared.getString("token", "token")!!
        profileViewModel.getUser(token)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val connectionObserver = Observer<String> {
            if (it == "No connection"){
                connection_error.visibility = View.VISIBLE
                content.isRefreshing = false
                progress_bar.visibility = View.GONE
            }
        }

        val userObserver =  Observer<GithubUser>{
            user = it
            content.isRefreshing = false
            content.isEnabled = false
            connection_error.visibility = View.GONE
            setUI().run { profileViewModel.getEvents(user.received_events_url) }
        }
        val eventsObserver = Observer<ArrayList<Event>> {
            setRecycler(it).run { progress_bar.visibility = View.INVISIBLE }
        }

        content.setOnRefreshListener {
            profileViewModel.getUser(token)
        }

        profileViewModel.connection.observe(this, connectionObserver)
        profileViewModel.events.observe(this, eventsObserver)
        profileViewModel.user.observe(this, userObserver)
    }

    private fun setUI() {
        name.text = user.name
        mail.text = user.email
        company.text = "${user.company}, ${user.location}"
        followers.text = "${user.followers}"
        following.text = "${user.following}"
        Glide.with(appContext)
            .load(user.avatar_url)
            .into(img)
    }

    private fun setRecycler(events: ArrayList<Event>) {
        viewManager = LinearLayoutManager(this.requireContext())
        viewAdapter = EventListAdapter(events)
        activity_recycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}