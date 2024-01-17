package com.example.foodorderapp.ui.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.foodorderapp.data.repo.FavoriteRepository
import com.example.foodorderapp.data.repo.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(var prepo : ProductsRepository,var favRepo : FavoriteRepository) : ViewModel() {

    private val _productsList = MutableLiveData<HashMap<String, List<Any>>>()
    val productsList: MutableLiveData<HashMap<String, List<Any>>> = _productsList

    init {
        loadProducts()
    }

    fun loadProducts() {
        CoroutineScope(Dispatchers.Main).launch {
           _productsList.value = prepo.loadProducts()
        }
    }


    fun addFavorite(productId : Int, productName : String, productImageName : String, productPrice : Int) {
        CoroutineScope(Dispatchers.Main).launch {
            favRepo.addFavorite(productId, productName, productImageName, productPrice)

        }
    }

    fun deleteFavorite(productId: Int) {
        CoroutineScope(Dispatchers.Main).launch{
            favRepo.deleteFavorite(productId)

        }
    }




}