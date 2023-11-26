package com.example.quickmart.data.repository

import android.content.Context
import android.util.Log
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.db.entities.ProductWithCategory
import com.example.quickmart.models.Product
import com.google.gson.Gson

object ProductRepository {
    private lateinit var db: QuickMartDatabase
    fun initDb(database: QuickMartDatabase) {
        db = database
    }

    fun initProducts(context: Context): List<Product> {
        val productsJson =
            context.assets.open("products.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        return gson.fromJson(productsJson, Array<Product>::class.java).toList()
    }

    fun getProductCategories(context: Context): List<String> {
        val categoriesJson =
            context.assets.open("product-categories.json").bufferedReader().use { it.readText() }
        val categories = Gson().fromJson(categoriesJson, Array<String>::class.java).toList()
        return listOf("All") + categories
    }

    suspend fun getProducts(name: String="", category: String = "All"): List<Product> {
        val productWithCategories = db.getQuickMartDao().getProducts(name, category)
        return productWithCategories.map { it.toProduct() }
    }

    suspend fun getCategoriesFromDb(): List<String> {
        return db.getQuickMartDao().getAllCategories().map { it.category }
    }

    private fun ProductWithCategory.toProduct(): Product {
        return Product(
            id = productEntity.id,
            title = productEntity.title,
            category = categoryEntity.category,
            description = productEntity.description,
            price = productEntity.price,
            rating = productEntity.rating,
            imageName = productEntity.imageName,
            isInFavourites = false
        )
    }
}