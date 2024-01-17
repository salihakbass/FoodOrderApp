package com.example.foodorderapp.data.entity

import com.google.gson.annotations.SerializedName

data class CartProductsResponse(
    @SerializedName("sepet_yemekler") var cartFoodList: List<CartProducts>,
    @SerializedName("success") var success: Int) {
}