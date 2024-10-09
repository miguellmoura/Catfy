package com.example.catify

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val email: String,
    val nickname: String,
    val password: String
)