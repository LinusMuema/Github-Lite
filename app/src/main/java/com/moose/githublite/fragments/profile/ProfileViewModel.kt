package com.moose.githublite.fragments.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.githublite.model.GithubEvents
import com.moose.githublite.model.GithubRepos
import com.moose.githublite.model.GithubUser
import com.moose.githublite.retrofit.GithubEndpoints
import com.moose.githublite.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    val user: MutableLiveData<GithubUser> by lazy {
        MutableLiveData<GithubUser>()
    }

    val events: MutableLiveData<List<GithubEvents>> by lazy {
        MutableLiveData<List<GithubEvents>>()
    }
    fun getUser(token: String) {
        val infoService =  ServiceBuilder.buildService(GithubEndpoints::class.java)
        val requestCall = infoService.getUser("Bearer $token")
        requestCall.enqueue(object : retrofit2.Callback<GithubUser> {
            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                if (response.isSuccessful)
                    user.value = response.body()
                else
                    Log.d("retrofit_", "unsuccessful ${response.message()}")
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                Log.d("retrofit_", t.toString())
            }
        })
    }

    fun getEvents(url: String) {
        val infoService =  ServiceBuilder.buildService(GithubEndpoints::class.java)
        val requestCall = infoService.getEvents(url)
        requestCall.enqueue(object : retrofit2.Callback<List<GithubEvents>> {
            override fun onResponse(call: Call<List<GithubEvents>>, response: Response<List<GithubEvents>>) {
                if (response.isSuccessful)
                    events.value = response.body()
                else
                    Log.d("events_", "unsuccessful ${response.message()}")
            }

            override fun onFailure(call: Call<List<GithubEvents>>, t: Throwable) {
                Log.d("events_", t.toString())
            }
        })
    }
}