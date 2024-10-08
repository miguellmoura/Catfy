package com.example.catify

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface UserDAO {
    @Insert
    fun insert(users:User)
    @Query("SELECT * FROM users")
    fun getAll(): List<User>
    @Update
    fun update(users : User)
    @Delete
    fun delete(users : User)
}