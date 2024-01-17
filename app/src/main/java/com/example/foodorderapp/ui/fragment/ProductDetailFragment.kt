package com.example.foodorderapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.data.entity.CartProducts
import com.example.foodorderapp.databinding.FragmentProductDetailBinding
import com.example.foodorderapp.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private val bundle : ProductDetailFragmentArgs by navArgs()
    private lateinit var binding : FragmentProductDetailBinding
    private lateinit var viewModel : DetailViewModel
    private var isFav = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_detail,container,false)

        binding.detailFragment = this

        val product = bundle.product

        binding.productObject = product

        isFav = bundle.isFav

        if(isFav) {
            binding.imgDetailFav.setImageResource(
                resources.getIdentifier(R.drawable.icon_fav.toString(),"drawable",requireContext().packageName))
        }

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${product.productImageName}"
        Glide.with(this).load(url).override(500,700).into(binding.imgDetailProduct)

        binding.cartObject = CartProducts(1,bundle.product.productName,bundle.product.productImageName,bundle.product.productPrice,1,"Salih")

        viewModel.cartProductObject.observe(viewLifecycleOwner) {
            binding.cartObject = it


        }
        return binding.root
    }

    fun addToCart(cartProductObject : CartProducts) {
        val username = "Salih"
        viewModel.addToCart(
            cartProductObject.productName,
            cartProductObject.productImageName,
            cartProductObject.productPrice,
            cartProductObject.productQuantity,
            username
        )
    }
    fun changeQuantity(action : String,cartObject : CartProducts) {
        viewModel.changeQuantity(action,cartObject,bundle.product.productPrice)
    }

    fun navBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    fun changeFavorite() {
        if(isFav) {
            viewModel.deleteFav(bundle.product.productId)
            binding.imgDetailFav.setImageResource(
                resources.getIdentifier(R.drawable.icon_not_fav.toString(),"drawable",requireContext().packageName)
            )
        }else{
            viewModel.addFav(bundle.product.productId,bundle.product.productName,bundle.product.productImageName,bundle.product.productPrice)
            binding.imgDetailFav.setImageResource(
                resources.getIdentifier(R.drawable.icon_fav.toString(),"drawable",requireContext().packageName)
            )
        }
        isFav = !isFav
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : DetailViewModel by viewModels()
        viewModel = tempViewModel
    }
}