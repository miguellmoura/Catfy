package com.example.catify

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    val userList = MutableLiveData<List<User>>()
    val _favCatsList = MutableLiveData<List<FavCats>>()
    val favCatsList: MutableLiveData<List<FavCats>> get() = _favCatsList
    fun addUser(user: User) {
        Singleton.addUser(user)
        userList.value = Singleton.userList
    }

    fun addFavCat(favCats: FavCats) {
        Singleton.addFavCat(favCats)
        viewModelScope.launch {
            val favCatsListResult = withContext(Dispatchers.IO) {
                Singleton.getFavCats(Singleton.getCurrentUser().id!!)
            }
            favCatsList.postValue(favCatsListResult)
        }
    }

    fun fetchFavCats (userId: Int) {
        viewModelScope.launch {
            val favCatsListResult = withContext(Dispatchers.IO) {
                Singleton.getFavCats(Singleton.getCurrentUser().id!!)
            }
            favCatsList.postValue(favCatsListResult)
        }
    }

    fun deleteFavCat(position: Int) {
        Log.d("FUI CHAMADO DELETEFAVCAT", "AAAAAAA")
        CoroutineScope(Dispatchers.IO).launch {
            Singleton.deleteFavCat(position)

            val updatedFavCatsList = Singleton.getFavCats(Singleton.getCurrentUser().id!!)

            withContext(Dispatchers.Main) {
                favCatsList.value = updatedFavCatsList
            }
        }
    }

}