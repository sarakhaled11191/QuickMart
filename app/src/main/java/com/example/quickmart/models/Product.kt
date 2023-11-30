package com.example.quickmart.models

data class Product(
    val id: String = "",
    val categoryId: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: Double = 0.0,
    val rating: Int = 0,
    val title: String = "",
    var isInFavourites: Boolean = false
)