package com.example.foodorderapp.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Products(
    @SerializedName("yemek_id") var productId : Int,
    @SerializedName("yemek_adi") var productName : String,
    @SerializedName("yemek_resim_adi") var productImageName : String,
    @SerializedName("yemek_fiyat") var productPrice : Int
) : Serializable
