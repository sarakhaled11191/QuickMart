package com.example.quickmart.data.repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import com.example.quickmart.models.Product
import com.google.gson.Gson
import java.util.Locale

object ProductRepository {
    private var originalProductList = listOf<Product>()
    var productList by mutableStateOf(listOf<Product>())

    fun initProducts(context: Context) {
        val productsJson =
            context.assets.open("products.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        originalProductList = gson.fromJson(productsJson, Array<Product>::class.java).toList()
        productList = originalProductList
    }

    fun getProducts(name: String, category: String = "All") {
        productList = if (category == "All") {
            originalProductList.filter {
                it.title.lowercase().contains(name.lowercase(), ignoreCase = true)
            }
        } else {
            originalProductList.filter {
                it.title.lowercase().contains(
                    name.lowercase(),
                    ignoreCase = true
                ) && it.category == category.lowercase()
            }
        }
    }

    fun getProductCategories(context: Context): List<String> {
        val categoriesJson =
            context.assets.open("product-categories.json").bufferedReader().use { it.readText() }
        val categories = Gson().fromJson(categoriesJson, Array<String>::class.java).toList()
        return listOf("All") + categories
    }
}