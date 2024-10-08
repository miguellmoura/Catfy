package com.example.catify

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface FavCatsDAO {
    @Insert
    fun insert(favCat:Cat)
    @Query("SELECT * FROM FavCats")
    fun getAll(): List<Cat>
    @Update
    fun update(favCat: Cat)
    @Delete
    fun delete(favCat: Cat)
}