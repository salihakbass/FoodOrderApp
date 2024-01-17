package com.example.foodorderapp.data.datasource

import com.example.foodorderapp.data.entity.FavoriteProducts
import com.example.foodorderapp.room.FavDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteDataSource(var fDao : FavDao) {

    suspend fun loadFavorite() : List<FavoriteProducts> =
        withContext(Dispatchers.IO) {
            return@withContext fDao.loadFavoriteProducts()
        }
    suspend fun addFavorite(
        productId : Int,
        productName : String,
        productImageName : String,
        productPrice : Int
    ) {
        val newFavorite = FavoriteProducts(0,productId, productName, productImageName, productPrice)
        fDao.addFavorite(newFavorite)
    }
    suspend fun deleteFavorite(productId: Int) {
        val favProductList = loadFavorite()
        favProductList.forEach { favoriteProducts ->
            if(favoriteProducts.productId == productId) {
                val deletedFavorite = FavoriteProducts(
                    favoriteProducts.favProductId,
                    favoriteProducts.productId,
                    favoriteProducts.productName,
                    favoriteProducts.productImageName,
                    favoriteProducts.productPrice
                )
                fDao.deleteFavorite(deletedFavorite)
            }
        }
    }

}