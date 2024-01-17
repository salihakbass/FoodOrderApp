package com.example.foodorderapp.data.repo

import com.example.foodorderapp.data.datasource.FavoriteDataSource
import com.example.foodorderapp.data.entity.FavoriteProducts

class FavoriteRepository(var fds : FavoriteDataSource) {
    suspend fun loadFavorite() : List<FavoriteProducts> = fds.loadFavorite()

    suspend fun addFavorite(productId : Int, productName : String, productImageName : String, productPrice : Int) =
        fds.addFavorite(productId, productName, productImageName, productPrice)

    suspend fun deleteFavorite(productId: Int) = fds.deleteFavorite(productId)


}