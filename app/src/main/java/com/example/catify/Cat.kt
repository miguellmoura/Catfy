package com.example.catify

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "FavCats")
class Cat(
    @PrimaryKey()
    val id: String,
    val name: String,
    val url: String,
    val description: String,
    val life_span: String,
    val adaptability: Int,
    val affection_level: Int,
    val energy_level: Int,
    val health_issues: Int,
    val intelligence: Int,
) : Serializable