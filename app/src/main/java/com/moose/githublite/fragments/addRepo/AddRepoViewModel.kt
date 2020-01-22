package com.moose.githublite.fragments.addRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moose.githublite.model.GithubRepos
import com.moose.githublite.retrofit.GithubEndpoints
import com.moose.githublite.retrofit.ServiceBuilder
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader


class AddRepoViewModel : ViewModel() {
    val created: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun addRepo(token: String, repo: RequestBody) {
        val infoService =  ServiceBuilder.buildService(GithubEndpoints::class.java)
        val requestCall = infoService.creatRepo("Bearer $token",repo)
        requestCall.enqueue(object : retrofit2.Callback<GithubRepos> {
            override fun onResponse(call: Call<GithubRepos>, response: Response<GithubRepos>) {
                if (response.isSuccessful) {
                    created.value = response.message()
                }
                else {
                    val ereader = BufferedReader(InputStreamReader(response.errorBody()!!.byteStream()))
                    var eline: String?
                    while (ereader.readLine().also { eline = it } != null) {
                        if (eline!!.contains("already exists"))
                            created.value = "It exists"
                    }
                }
            }

            override fun onFailure(call: Call<GithubRepos>, t: Throwable) {
                Log.d("retrofit_", t.toString())
            }
        })
    }
}