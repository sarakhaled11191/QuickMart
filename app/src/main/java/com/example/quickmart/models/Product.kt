package com.example.quickmart.models

import java.io.Serializable

data class Product(
    val id : String,
    val title: String,
    val category: String,
    val description: String,
    val price: Double,
    val rating: Int,
    val imageName: String,
    var isInFavourites : Boolean = false
) : Serializable
