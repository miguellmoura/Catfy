package com.example.catify

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface FavCatsDAO {
    @Insert
    fun insert(favCat:CatBreed)
    @Query("SELECT * FROM FavCats")
    fun getAll(): List<CatBreed>
    @Update
    fun update(favCat: CatBreed)
    @Delete
    fun delete(favCat: CatBreed)
}