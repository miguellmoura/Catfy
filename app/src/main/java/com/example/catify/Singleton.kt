package com.example.catify

import android.content.Context


object Singleton {
    lateinit var favCatsList : List<CatBreed>
    private lateinit var dao: FavCatsDAO
    fun setContext (context: Context) {
        FavCatsDatabase.getInstance(context)?.let {
            dao = it.favCatsDAO()
            favCatsList = dao.getAll()
        }
    }
    fun addFavCat(favCat : CatBreed) {
        dao.insert(favCat)
        favCatsList = dao.getAll()
    }
    fun updateFavCat(favCat : CatBreed) {
        dao.update(favCat)
        favCatsList = dao.getAll()
    }
    fun deleteFavCat(favCat : CatBreed) {
        dao.delete(favCat)
        favCatsList = dao.getAll()
    }


    var texto = "ok"

}