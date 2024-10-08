package com.example.catify

import android.content.Context

object Singleton {

    var texto = "ok"
    var catsList = ArrayList<Cat>()
    private lateinit var favCatsList : List<Cat>
    private lateinit var userList : List<User>
    private lateinit var dao: FavCatsDAO
    private lateinit var daoUser : UserDAO
    fun setContext (context: Context) {
        FavCatsDatabase.getInstance(context)?.let {
            dao = it.favCatsDAO()
            favCatsList = dao.getAll()
        }
    }
    fun setContextUser (context: Context) {
        UserDatabase.getInstance(context)?.let {
            daoUser = it.UserDAO()
            userList = daoUser.getAll()
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
    fun addUser(user : User) {
        daoUser.insert(user)
        userList = daoUser.getAll()
    }
    fun updateUser(user : User) {
        daoUser.update(user)
        userList = daoUser.getAll()
    }
    fun deleteUser(user : User) {
        daoUser.delete(user)
        userList = daoUser.getAll()
    }

}