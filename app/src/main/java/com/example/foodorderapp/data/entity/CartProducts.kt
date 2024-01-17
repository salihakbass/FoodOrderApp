package com.example.foodorderapp.data.entity

import com.google.gson.annotations.SerializedName

data class CartProducts(
    @SerializedName("sepet_yemek_id") var cartProductId: Int,
    @SerializedName("yemek_adi") var productName: String,
    @SerializedName("yemek_resim_adi") var productImageName: String,
    @SerializedName("yemek_fiyat") var productPrice: Int,
    @SerializedName("yemek_siparis_adet") var productQuantity: Int,
    @SerializedName("kullanici_adi") var username: String) {
}