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