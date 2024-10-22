package com.example.catify

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface FavCatsDAO {
    @Insert
    fun insert(favCat:FavCats)

    @Query("SELECT * FROM FavCats")
    fun getAll(): List<FavCats>
    @Update
    fun update(favCat: FavCats)
    @Delete
    fun delete(favCat: FavCats)

    @Query("SELECT * FROM FavCats WHERE idUsuario = :idUsuario")
    suspend fun obterGatosFavoritos(idUsuario: Int): List<FavCats>
}

