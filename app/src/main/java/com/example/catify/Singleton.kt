package com.example.catify

import android.content.Context
import android.util.Log

object Singleton {

    var texto = "ok"
    var catsList = ArrayList<Cat>()
    private lateinit var favCatsList: List<Cat>
    lateinit var userList: List<User>
    private lateinit var dao: FavCatsDAO
    private lateinit var daoUser: UserDAO

    fun setContext(context: Context) {
        FavCatsDatabase.getInstance(context)?.let {
            Log.d("entrada", "setcontext normal")
            dao = it.favCatsDAO()
            favCatsList = dao.getAll()
        }
    }

    fun setContextUser(context: Context) {
        UserDatabase.getInstance(context)?.let {
            Log.d("entrada", "setcontext user")
            daoUser = it.UserDAO()
            userList = daoUser.getAll()
        }
    }

    fun initialize(daoUser: UserDAO) {
        this.daoUser = daoUser
    }

    fun addFavCat(favCat: Cat) {
        dao.insert(favCat)
        favCatsList = dao.getAll()
    }

    fun updateFavCat(favCat: Cat) {
        dao.update(favCat)
        favCatsList = dao.getAll()
    }

    fun deleteFavCat(favCat: Cat) {
        dao.delete(favCat)
        favCatsList = dao.getAll()
    }

    fun addUser(user: User) {
        daoUser.insert(user)
        userList = daoUser.getAll()
    }

    fun getUser(email: String): User? {
        if (!::daoUser.isInitialized) {
            throw UninitializedPropertyAccessException("daoUser não foi inicializado")
        }
        return daoUser.getUser(email)
    }

    fun updateUser(user: User) {
        daoUser.update(user)
        userList = daoUser.getAll()
    }

    fun deleteUser(user: User) {
        daoUser.delete(user)
        userList = daoUser.getAll()
    }
}