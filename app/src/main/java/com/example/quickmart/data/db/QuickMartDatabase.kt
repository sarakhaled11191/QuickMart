package com.example.quickmart.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quickmart.data.db.entities.CartEntity
import com.example.quickmart.data.db.entities.CategoryEntity
import com.example.quickmart.data.db.entities.FavouriteEntity
import com.example.quickmart.data.db.entities.ProductEntity
import com.example.quickmart.data.db.entities.UserEntity

@Database(
    entities = [CartEntity::class, FavouriteEntity::class, ProductEntity::class, CategoryEntity::class, UserEntity::class],
    version = 1
)
abstract class QuickMartDatabase : RoomDatabase() {
    abstract fun getQuickMartDao(): QuickMartDao

    companion object {
        private const val DataBaseName = "QuickMartDatabase"
        private val LOCK = Any()

        @Volatile
        private var myDB: QuickMartDatabase? = null

        operator fun invoke(context: Context) = myDB ?: synchronized(lock = LOCK) {
            myDB ?: createDB(context).also { myDB = it }
        }

        private fun createDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                QuickMartDatabase::class.java,
                DataBaseName
            ).fallbackToDestructiveMigration().build()
    }
}