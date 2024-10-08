package com.example.catify

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatBreedAPI {
    @GET("images/search")
    suspend fun getCat(
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String,
        @Query("has_breeds") has_breeds: Int,
    ): List<CatResponse>

}