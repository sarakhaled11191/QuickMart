package com.example.quickmart

import android.app.Application
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.models.User
import com.example.quickmart.utils.appUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val database = QuickMartDatabase(this)
        val dao = database.getQuickMartDao()
        CoroutineScope(Dispatchers.IO).launch {
            val entity = dao.getUser()
            appUser = if (entity != null) {
                User(
                    id = entity.id,
                    firstName = entity.firstName,
                    lastName = entity.lastName,
                    email = entity.email,
                    admin = entity.isAdmin,
                    profilePic = entity.profilePic,
                )
            } else {
                null
            }
        }
    }
}