package com.example.catify

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

data class UserRoom (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val nickname: String,
    val password: String
)