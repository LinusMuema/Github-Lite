package com.moose.githublite.model

data class GithubRepos(
    val description: String,
    val language: String,
    val license: License?,
    val name: String,
    val `private`: Boolean,
    val created_at:String,
    val stargazers_count:Int
)

data class License(
    val name: String
)

data class GithubUser(
    val avatar_url: String,
    val company: String,
    val email: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val location: String,
    val name: String,
    val type: String,
    val received_events_url:String
)

data class GithubEvents(
    val actor: Actor,
    val created_at: String,
    val repo: Repo,
    val type: String
)

data class Actor(
    val avatar_url: String,
    val login: String
)

data class Repo(
    val name: String
)

