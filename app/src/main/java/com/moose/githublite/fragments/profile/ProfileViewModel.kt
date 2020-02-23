package com.moose.githublite.fragments.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.githublite.model.Event
import com.moose.githublite.model.GithubEvents
import com.moose.githublite.model.GithubUser
import com.moose.githublite.retrofit.GithubEndpoints
import com.moose.githublite.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    val user: MutableLiveData<GithubUser> by lazy {
        MutableLiveData<GithubUser>()
    }

    val connection:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val events: MutableLiveData<ArrayList<Event>> by lazy {
        MutableLiveData<ArrayList<Event>>()
    }

    fun getUser(token: String) {
        val infoService =  ServiceBuilder.buildService(GithubEndpoints::class.java)
        val requestCall = infoService.getUser("Bearer $token")
        requestCall.enqueue(object : retrofit2.Callback<GithubUser> {
            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                connection.value = "Connection ok"
                if (response.isSuccessful)
                    user.value = response.body()
                else
                    Log.d("retrofit_", "unsuccessful ${response.message()}")
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                if (t.message!!.contains("Unable to resolve host"))
                    connection.value = "No connection"
            }
        })
    }

    fun getEvents(url: String) {
        val infoService =  ServiceBuilder.buildService(GithubEndpoints::class.java)
        val requestCall = infoService.getEvents(url)
        requestCall.enqueue(object : retrofit2.Callback<List<GithubEvents>> {
            override fun onResponse(call: Call<List<GithubEvents>>, response: Response<List<GithubEvents>>) {
                if (response.isSuccessful)
                    prepareEvents(response.body()!!)
                else
                    Log.d("events_", "unsuccessful ${response.message()}")
            }

            override fun onFailure(call: Call<List<GithubEvents>>, t: Throwable) {
                Log.d("events_", t.toString())
            }
        })
    }

    private fun prepareEvents(response: List<GithubEvents>) {
        val arrayList: ArrayList<Event> = ArrayList()

        for (event in response){
            val image = event.actor.avatar_url
            var message = ""
            when (event.type) {
                "CreateEvent" -> message = "${event.actor.login} created ${event.repo.name.split("/")[1]} on ${event.created_at.split("T")[0]}"
                "ForkEvent" -> message = "${event.actor.login} forked ${event.repo.name.split("/")[1]} on ${event.created_at.split("T")[0]}"
                "WatchEvent" -> message = "${event.actor.login} starred ${event.repo.name.split("/")[1]} on ${event.created_at.split("T")[0]}"
                "DeleteEvent" -> message = "${event.actor.login} deleted ${event.repo.name.split("/")[1]} on ${event.created_at.split("T")[0]}"
                "PublicEvent" -> message = "${event.actor.login} made ${event.repo.name} public on ${event.created_at.split("T")[0]}"
            }
            if (message.isNotEmpty())
                arrayList.add(Event(image, message))
        }
        events.value = arrayList
    }
}