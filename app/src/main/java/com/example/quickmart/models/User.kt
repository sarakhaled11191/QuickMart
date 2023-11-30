package com.example.quickmart.models

data class User(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val admin: Boolean = false,
    val profilePic: String? = null
)