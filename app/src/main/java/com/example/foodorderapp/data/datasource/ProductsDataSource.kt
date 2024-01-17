package com.example.foodorderapp.data.datasource

import com.example.foodorderapp.data.entity.CartProducts
import com.example.foodorderapp.data.entity.FavoriteProducts
import com.example.foodorderapp.data.entity.Products
import com.example.foodorderapp.retrofit.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsDataSource(var pdao : ProductDao,var fds : FavoriteDataSource) {

    suspend fun loadProducts() : HashMap<String, List<Any>> =
        withContext(Dispatchers.IO) {
            val productList = pdao.loadProduct().products
            val favList = fds.loadFavorite()

            val tempHashMap = HashMap<String,List<Any>>()
            tempHashMap["Products"] = productList
            tempHashMap["Favorites"] = favList

            return@withContext tempHashMap
        }

   suspend fun loadCart(username : String) : List<CartProducts> =
       withContext(Dispatchers.IO) {
            addToCart("error","error",0,0,username,"error")
           val tempProductList = pdao.loadCart(username).cartFoodList
           var errorProductId = 0
           var cartProductList = ArrayList<CartProducts>()

           tempProductList.forEach {
               if(it.productName == "error") {
                   errorProductId = it.cartProductId
               }else{
                    cartProductList.add(it)
               }
           }
            deleteCart(errorProductId,username)
            cartProductList.sortBy { it.productName }
           return@withContext cartProductList

           // return@withContext pdao.loadCart(username).cartFoodList
       }

    suspend fun addToCart(foodName : String,foodImageName : String,foodPrice : Int,foodQuantity: Int,username : String,action : String) {
        when(action) {
            "Detay" -> {
                var newQuantity = foodQuantity
                val cartProductList = loadCart(username)
                cartProductList.forEach { cartProducts ->
                    if(cartProducts.productName == foodName) {
                        newQuantity += cartProducts.productQuantity
                        deleteCart(cartProducts.cartProductId,username)
                    }
                }
                pdao.addToCart(foodName,foodImageName,foodPrice,newQuantity,username)
            }
            else -> {
                pdao.addToCart(foodName,foodImageName,foodPrice,foodQuantity,username)
            }
        }
    }
    suspend fun deleteCart(cartProductId : Int, username : String) {
        pdao.deleteCart(cartProductId, username)
    }
    suspend fun calculateTotalPrice(cartProductList : List<CartProducts>) : String =
        withContext(Dispatchers.IO) {
            var totalPrice = 0
            cartProductList.forEach { cartProducts ->
                totalPrice += (cartProducts.productPrice * cartProducts.productQuantity)
            }
            return@withContext totalPrice.toString()
        }
    suspend fun confirmCart(cartList : List<CartProducts>,username: String) {
        cartList.forEach { cartProducts ->
            deleteCart(cartProducts.cartProductId,username)
        }
    }

}