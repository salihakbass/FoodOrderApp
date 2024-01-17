package com.example.foodorderapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodorderapp.data.entity.FavoriteProducts

@Dao
interface FavDao {
    @Query("SELECT * FROM favorite_food ORDER BY product_name")
    suspend fun loadFavoriteProducts() : List<FavoriteProducts>

    @Insert
    suspend fun addFavorite(favProducts : FavoriteProducts)

    @Delete
    suspend fun deleteFavorite(favProducts : FavoriteProducts)



}