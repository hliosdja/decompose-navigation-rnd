package com.example.decompose_navigation_rnd.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val name: String = "",
    val email: String = "",
    val job: String = ""
)