package com.example.foodorderapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "favorite_food")
data class FavoriteProducts(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("favorite_product_id") var favProductId : Int,
    @ColumnInfo("product_id")  var productId : Int,
    @ColumnInfo("product_name")  var productName : String,
    @ColumnInfo("product_image_name")  var productImageName : String,
    @ColumnInfo("product_price")  var productPrice : Int,

) {
}