package com.example.catify

data class Cat(
    val breeds: List<CatBreed>,
    val id: String,
    val url: String,
)