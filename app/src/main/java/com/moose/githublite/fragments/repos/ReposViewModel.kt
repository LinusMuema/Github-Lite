package com.moose.githublite.fragments.repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.githublite.model.GithubRepos
import com.moose.githublite.retrofit.GithubEndpoints
import com.moose.githublite.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class ReposViewModel : ViewModel() {
    val repos:MutableLiveData<List<GithubRepos>> by lazy {
        MutableLiveData<List<GithubRepos>>()
    }
    fun getRepos(token: String) {
        val infoService =  ServiceBuilder.buildService(GithubEndpoints::class.java)
        val requestCall = infoService.getRepositories("Bearer $token")
        requestCall.enqueue(object : retrofit2.Callback<List<GithubRepos>> {
            override fun onResponse(call: Call<List<GithubRepos>>, response: Response<List<GithubRepos>>) {
                if (response.isSuccessful)
                   repos.value = response.body()
                else
                    Log.d("retrofit_", "unsuccessful ${response.message()}")
            }

            override fun onFailure(call: Call<List<GithubRepos>>, t: Throwable) {
                Log.d("retrofit_", t.toString())
            }
        })
    }
}