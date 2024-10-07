package com.example.catify

data class CatBreed(
    val categories: List<Category>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)

data class Category(
    val id: Int,
    val name: String
)
