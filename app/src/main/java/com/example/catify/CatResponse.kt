package com.example.catify

data class CatResponse (
    val breeds: List<CatBreedData>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
