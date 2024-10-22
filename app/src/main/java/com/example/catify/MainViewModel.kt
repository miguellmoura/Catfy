package com.example.catify

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    val userList = MutableLiveData<List<User>>()
    val favCatsList = MutableLiveData<List<FavCats>>()
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
            favCatsList.value = favCatsListResult
        }
    }
}