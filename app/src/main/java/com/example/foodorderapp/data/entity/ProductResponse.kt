package com.example.foodorderapp.data.entity

import com.google.gson.annotations.SerializedName

class ProductResponse(
    @SerializedName("yemekler")var products: List<Products>,
    @SerializedName("success")var success: Int
) {

}

