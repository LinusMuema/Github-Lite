package com.moose.githublite.fragments.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun getUser(token: String) {
        val infoService =  ServiceBuilder.buildService(GithubEndpoints::class.java)
        val requestCall = infoService.getUSer("Bearer $token")
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
}