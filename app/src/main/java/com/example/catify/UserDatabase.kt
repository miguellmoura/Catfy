package com.example.catify

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDAO() : UserDAO
    companion object {
        const val DATABASE_NAME = "user_database"
        private var instance : UserDatabase? = null
        fun getInstance(context: Context): UserDatabase? {
            if( instance == null ) {
                synchronized(UserDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        DATABASE_NAME
                    ).allowMainThreadQueries().build()
                }
            }
            return instance!!
        }

    }
}