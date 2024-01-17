package com.example.foodorderapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorderapp.data.entity.CartProducts
import com.example.foodorderapp.data.repo.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel  @Inject constructor(var prepo : ProductsRepository)     : ViewModel() {

    var cartProductList = MutableLiveData<List<CartProducts>>()
    val _cartProductList : MutableLiveData<List<CartProducts>> = cartProductList

    private val _totalPrice = MutableLiveData<String>()
    val totalPrice: MutableLiveData<String> = _totalPrice

    fun loadCart(username : String) {
        CoroutineScope(Dispatchers.Main).launch {
            cartProductList.value = prepo.loadCart(username)
            calculateTotalPrice()
        }
    }

    fun deleteCart(cartProductId : Int, username : String) {
        CoroutineScope(Dispatchers.Main).launch {
            prepo.deleteCart(cartProductId, username)
            loadCart("Salih")
        }
    }
    fun calculateTotalPrice() {
        val cartProductList = _cartProductList.value as List<CartProducts>
        CoroutineScope(Dispatchers.Main).launch {
            _totalPrice.value = prepo.calculateTotalPrice(cartProductList)
        }
    }
    fun confirmCart(username : String) {
        val cartList = _cartProductList.value as List<CartProducts>
        CoroutineScope(Dispatchers.Main).launch {
            prepo.confirmCart(cartList, username)
            _cartProductList.value = arrayListOf()
            _totalPrice.value = ""
        }
    }
    fun incDecAction(
        action : String,
        cartProductId : Int,
        productName : String,
        productImageName: String,
        productPrice: Int,
        productQuantity: Int,
        username: String
    ) {
        when (action) {
            "Delete" -> {
                CoroutineScope(Dispatchers.Main).launch {
                    deleteCart(cartProductId, username)
                    loadCart(username)
                }
            }

            else -> {
                CoroutineScope(Dispatchers.Main).launch {
                    deleteCart(cartProductId, username)
                    prepo.addToCart(
                        productName,
                        productImageName,
                        productPrice,
                        productQuantity,
                        username,
                        "Decrease-Increase"
                    )
                    loadCart(username)
                }
            }
        }
    }
}