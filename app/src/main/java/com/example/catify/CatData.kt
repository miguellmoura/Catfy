package com.example.catify

data class CatData (
    val id: Int,
    val url: String,
    val name: String,
    val breeds: List<CatBreedData>
)
