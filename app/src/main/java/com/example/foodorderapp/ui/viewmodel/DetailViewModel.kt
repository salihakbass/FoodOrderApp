package com.example.foodorderapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorderapp.data.entity.CartProducts
import com.example.foodorderapp.data.repo.FavoriteRepository
import com.example.foodorderapp.data.repo.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(var prepo : ProductsRepository,var frepo : FavoriteRepository) : ViewModel() {

    var addCartStatus = MutableLiveData<Boolean>()


    private val _cartProductObject = MutableLiveData<CartProducts>()
    var cartProductObject : MutableLiveData<CartProducts> = _cartProductObject

    fun addToCart(foodName : String,foodImageName : String,foodPrice : Int,foodQuantity: Int,username : String) {
        CoroutineScope(Dispatchers.Main).launch {
            prepo.addToCart(foodName,foodImageName,foodPrice/foodQuantity,foodQuantity,username,"Detay")
            addCartStatus.value = true
        }
    }

    fun changeQuantity(action : String,tempCartObject : CartProducts,price : Int) {
        when(action) {
            "Decrease" -> {
                if(tempCartObject.productQuantity != 1) {
                    tempCartObject.productQuantity -= 1
                }
            }
            "Increase" -> {
                tempCartObject.productQuantity += 1
            }
        }
        tempCartObject.productPrice = tempCartObject.productQuantity * price
        _cartProductObject.value = tempCartObject
    }

    fun addFav(productId : Int,productName : String,productImageName : String,productPrice : Int) {
        CoroutineScope(Dispatchers.Main).launch {
            frepo.addFavorite(productId, productName, productImageName, productPrice)
        }
    }
    fun deleteFav(productId : Int) {
        CoroutineScope(Dispatchers.Main).launch {
            frepo.deleteFavorite(productId)
        }
    }
}