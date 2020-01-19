package com.moose.githublite.fragments.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.moose.githublite.R
import com.moose.githublite.model.GithubUser
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var shared:SharedPreferences
    private lateinit var user: GithubUser
    private lateinit var token:String
    private lateinit var name:TextView
    private lateinit var mail:TextView
    private lateinit var company:TextView
    private lateinit var followers:TextView
    private lateinit var following:TextView
    private lateinit var img:CircleImageView
    private lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        appContext = activity!!.applicationContext
        shared = activity?.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        token = shared.getString("token", "token")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        name = view.findViewById(R.id.name)
        mail = view.findViewById(R.id.mail)
        company = view.findViewById(R.id.company)
        followers = view.findViewById(R.id.followers)
        following = view.findViewById(R.id.following)
        img = view.findViewById(R.id.img)
        val userObserver =  Observer<GithubUser>{
            user = it
            setUI().run {
                view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.INVISIBLE
            }
        }
        profileViewModel.user.observe(this, userObserver)
        profileViewModel.getUser(token)
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setUI() {
        name.text = "${user.type} : ${user.name}"
        mail.text = "Email : ${user.email}"
        company.text = "${user.company}, ${user.location}"
        followers.text = "Followers : ${user.followers}"
        following.text = "Following : ${user.following}"
        Glide.with(appContext)
            .load(user.avatar_url)
            .into(img)
    }
}