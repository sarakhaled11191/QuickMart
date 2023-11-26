package com.example.quickmart.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val firstName: String,
    @ColumnInfo
    val lastName: String,
    @ColumnInfo
    val email: String,
    @ColumnInfo
    val isAdmin: Boolean = false,
    @ColumnInfo
    val profilePic: String? = null
)