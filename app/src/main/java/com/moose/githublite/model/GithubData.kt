package com.moose.githublite.model

data class GithubRepos(
    val description: String,
    val language: String,
    val license: License,
    val name: String,
    val `private`: Boolean
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
    val type: String
)