package com.example.catify

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavCats")

data class FavCatsRoom (
    @PrimaryKey(autoGenerate = true)
    val breeds: List<CatBreed>,
    val id: String,
    val url: String
)