package com.example.quickmart.models

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val isAdmin: Boolean = false,
    val profilePic: String? = null
)