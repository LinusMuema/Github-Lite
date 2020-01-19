package com.moose.githublite.retrofit

import com.moose.githublite.model.GithubRepos
import com.moose.githublite.model.GithubUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GithubEndpoints {
    @GET("user/repos")
    fun getRepositories(@Header("Authorization") token: String): Call<List<GithubRepos>>

    @GET("user")
    fun getUSer(@Header("Authorization") token: String): Call<GithubUser>
}