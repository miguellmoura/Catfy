package com.example.catify

import java.io.Serializable

class Cat(
    val breeds: List<CatBreed>,
    val id: String,
    val url: String,
) : Serializable