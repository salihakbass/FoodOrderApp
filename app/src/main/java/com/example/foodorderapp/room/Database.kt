package com.example.foodorderapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodorderapp.data.entity.FavoriteProducts

@Database(entities = [FavoriteProducts::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun getFavDao() : FavDao
}