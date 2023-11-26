package com.example.quickmart

import android.app.Application
import com.example.quickmart.data.AppPreferences
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.db.entities.CategoryEntity
import com.example.quickmart.data.db.entities.ProductEntity
import com.example.quickmart.data.db.entities.UserEntity
import com.example.quickmart.data.repository.ProductRepository
import com.example.quickmart.models.User
import com.example.quickmart.utils.appUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appPreferences = AppPreferences(this)
        val productRepository = ProductRepository
        val database = QuickMartDatabase(this)
        productRepository.initDb(database)
        val dao = database.getQuickMartDao()
        if (!appPreferences.isDatabaseSeeded()) {
            CoroutineScope(Dispatchers.IO).launch {
                dao.addUser(
                    UserEntity(
                        "test",
                        "test",
                        "test",
                        "test",
                    )
                )
                val userEntity = dao.getUser()
                appUser = User(
                    id = userEntity.id,
                    firstName = userEntity.firstName,
                    lastName = userEntity.lastName,
                    email = userEntity.email
                )


                val categories = ProductRepository.getProductCategories(this@MyApp)
                categories.forEach { dao.addCategory(CategoryEntity(category = it)) }

                val seededCategories = dao.getAllCategories()

                val products = ProductRepository.initProducts(this@MyApp)
                products.forEach { product ->
                    val category = seededCategories.find {
                        it.category.equals(
                            product.category,
                            ignoreCase = true
                        )
                    }
                    if (category != null) {
                        val productEntity = ProductEntity(
                            id = product.id,
                            title = product.title,
                            description = product.description,
                            price = product.price,
                            rating = product.rating,
                            imageName = product.imageName,
                            categoryId = category.id
                        )
                        dao.addProduct(productEntity)
                    }
                }

                appPreferences.setDatabaseSeeded()
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val userEntity = dao.getUser()
                appUser = User(
                    id = userEntity.id,
                    firstName = userEntity.firstName,
                    lastName = userEntity.lastName,
                    email = userEntity.email
                )
            }
        }
    }
}
