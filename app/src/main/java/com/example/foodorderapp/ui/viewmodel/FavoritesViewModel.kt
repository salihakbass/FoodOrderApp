package com.example.foodorderapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorderapp.data.entity.FavoriteProducts
import com.example.foodorderapp.data.repo.FavoriteRepository
import com.example.foodorderapp.data.repo.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel  @Inject constructor(var prepo : ProductsRepository,var favRepo : FavoriteRepository) : ViewModel() {
    private val _addCartStatus = MutableLiveData<Boolean>()
    val addCartStatus: MutableLiveData<Boolean> = _addCartStatus

    private val _favoriteList = MutableLiveData<List<FavoriteProducts>>()
    val favoriteList: MutableLiveData<List<FavoriteProducts>> = _favoriteList

    init {
        loadFav()
    }

    fun addToCart(foodName : String,foodImageName : String,foodPrice : Int,foodQuantity: Int,username : String) {
        CoroutineScope(Dispatchers.Main).launch {
            prepo.addToCart(foodName,foodImageName,foodPrice/foodQuantity,foodQuantity,username,"Detay")
            _addCartStatus.value = true
        }
    }
    fun loadFav() {
        CoroutineScope(Dispatchers.Main).launch {
            _favoriteList.value = favRepo.loadFavorite()
        }
    }
    fun deleteFav(productId : Int) {
        CoroutineScope(Dispatchers.Main).launch {
            favRepo.deleteFavorite(productId)
            loadFav()
        }
    }

}