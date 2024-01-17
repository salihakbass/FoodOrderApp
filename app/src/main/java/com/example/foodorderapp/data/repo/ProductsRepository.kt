package com.example.foodorderapp.data.repo

import com.example.foodorderapp.data.datasource.ProductsDataSource
import com.example.foodorderapp.data.entity.CartProducts
import com.example.foodorderapp.data.entity.Products

class ProductsRepository(var pds : ProductsDataSource) {

    suspend fun loadProducts() : HashMap<String,List<Any>> = pds.loadProducts()

    suspend fun loadCart(username : String) : List<CartProducts> = pds.loadCart(username)

    suspend fun addToCart(foodName : String,foodImageName : String,foodPrice : Int,foodQuantity: Int,username : String,action : String) {
        pds.addToCart(foodName, foodImageName, foodPrice, foodQuantity, username, action)
    }

    suspend fun deleteCart(cartProductId : Int, username : String) = pds.deleteCart(cartProductId, username)

    suspend fun calculateTotalPrice(cartProductList : List<CartProducts>) : String = pds.calculateTotalPrice(cartProductList)

    suspend fun confirmCart(cartList : List<CartProducts>,username: String) = pds.confirmCart(cartList,username)


}