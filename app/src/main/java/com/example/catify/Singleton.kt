package com.example.catify

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Singleton {

    var texto = "ok"
    var catsList = ArrayList<Cat>()
    private lateinit var favCatsList: List<FavCats>
    lateinit var userList: List<User>
    private lateinit var dao: FavCatsDAO
    private lateinit var daoUser: UserDAO
    private lateinit var currentUser: User

    fun setCurrentUser(user: User) {
        currentUser = user
    }

    fun getCurrentUser(): User {
        return currentUser
    }

    fun setContextUser(context: Context) {
        UserDatabase.getInstance(context)?.let {
            Log.d("entrada", "setcontext user")
            daoUser = it.UserDAO()
            dao = it.FavCatsDAO()
            favCatsList = dao.getAll()
            userList = daoUser.getAll()
        }
    }

    fun initialize(daoUser: UserDAO) {
        this.daoUser = daoUser
    }

    fun addFavCat(favCat: FavCats) {
        dao.insert(favCat)
        favCatsList = dao.getAll()
    }

    suspend fun getFavCats(id: Int): List<FavCats> {
        return dao.obterGatosFavoritos(id)
    }

    fun deleteFavCat(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            val catToDelete = favCatsList[position]
            dao.delete(catToDelete)
            val updatedFavCatsList = dao.getAll()

            withContext(Dispatchers.Main) {
                favCatsList = updatedFavCatsList
            }
        }
    }

    fun getFavCat(position: Int): FavCats {
        Log.d("TESTEYARD", favCatsList.toString())
        return favCatsList[position]
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
}