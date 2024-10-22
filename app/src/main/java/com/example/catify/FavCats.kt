package com.example.catify

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavCats")

data class FavCats(
    @PrimaryKey(autoGenerate = true)
    val idFav: Int? = null,
    val id: String,
    val idUsuario: Int,
    val url: String,
    val name: String,
    val description: String,
    val life_span: String,
    val adaptability: Int,
    val affection_level: Int,
    val energy_level: Int,
    val health_issues: Int,
    val intelligence: Int,
)