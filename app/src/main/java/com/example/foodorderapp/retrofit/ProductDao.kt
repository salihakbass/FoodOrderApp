package com.example.foodorderapp.retrofit

import com.example.foodorderapp.data.entity.CRUDResponse
import com.example.foodorderapp.data.entity.CartProductsResponse
import com.example.foodorderapp.data.entity.ProductResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductDao {
    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
    //http://kasimadalan.pe.hu/ -> base url
    //yemekler/tumYemekleriGetir.php -webservis url

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun loadProduct() : ProductResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(@Field("yemek_adi") cartProductName : String,
                          @Field("yemek_resim_adi") cartProductImageName : String,
                          @Field("yemek_fiyat")  cartProductPrice : Int,
                          @Field("yemek_siparis_adet") cartProductQouantity : Int,
                          @Field("kullanici_adi") username : String) : CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun loadCart(@Field("kullanici_adi") username: String) : CartProductsResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteCart(@Field("sepet_yemek_id") cartProductId : Int,
                         @Field("kullanici_adi") username: String) : CRUDResponse
}