package com.example.catify

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class FavCatsDatabase : RoomDatabase() {
    abstract fun favCatsDAO() : FavCatsDAO
    companion object {
        const val DATABASE_NAME = "favcats_database"
        private var instance : FavCatsDatabase? = null
        fun getInstance(context: Context): FavCatsDatabase? {
            if( instance == null ) {
                synchronized(FavCatsDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavCatsDatabase::class.java,
                        DATABASE_NAME
                    ).allowMainThreadQueries().build()
                }
            }
            return instance!!
        }

    }
}