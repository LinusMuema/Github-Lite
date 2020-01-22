package com.moose.githublite.retrofit

import com.moose.githublite.model.GithubEvents
import com.moose.githublite.model.GithubRepos
import com.moose.githublite.model.GithubUser
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface GithubEndpoints {
    @GET("user/repos")
    fun getRepositories(@Header("Authorization") token: String): Call<List<GithubRepos>>

    @GET("user")
    fun getUser(@Header("Authorization") token: String): Call<GithubUser>

    @GET
    fun getEvents(@Url url:String): Call<List<GithubEvents>>

    @POST("user/repos")
    fun creatRepo(@Header("Authorization") token: String, @Body repo:RequestBody) : Call<GithubRepos>
}