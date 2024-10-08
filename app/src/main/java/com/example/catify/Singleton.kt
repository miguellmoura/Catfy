package com.example.catify

import android.content.Context

object Singleton {

    var texto = "ok"
    var catsList = ArrayList<Cat>()
    lateinit var favCatsList : List<Cat>
    private lateinit var dao: FavCatsDAO
    fun setContext (context: Context) {
        FavCatsDatabase.getInstance(context)?.let {
            dao = it.favCatsDAO()
            favCatsList = dao.getAll()
        }
    }
    fun addFavCat(favCat : Cat) {
        dao.insert(favCat)
        favCatsList = dao.getAll()
    }
    fun updateFavCat(favCat : Cat) {
        dao.update(favCat)
        favCatsList = dao.getAll()
    }
    fun deleteFavCat(favCat : Cat) {
        dao.delete(favCat)
        favCatsList = dao.getAll()
    }

}