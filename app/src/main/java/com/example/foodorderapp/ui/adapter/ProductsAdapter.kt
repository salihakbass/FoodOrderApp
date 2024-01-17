package com.example.foodorderapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.data.entity.FavoriteProducts
import com.example.foodorderapp.data.entity.Products
import com.example.foodorderapp.databinding.CardViewHomepageBinding
import com.example.foodorderapp.ui.fragment.HomePageFragmentDirections
import com.example.foodorderapp.ui.viewmodel.HomePageViewModel
import com.example.foodorderapp.utils.changePage

class ProductsAdapter(
    var mContext: Context,
    var productList: List<Products>,
    var favList: List<FavoriteProducts>,
    var viewModel: HomePageViewModel
) :
    RecyclerView.Adapter<ProductsAdapter.CardViewHolder>() {

    inner class CardViewHolder(var binding: CardViewHomepageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: CardViewHomepageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_view_homepage, parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val product = productList.get(position)
        val binding = holder.binding
        var isFav = false



        binding.productObject = product

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${product.productImageName}"
        Glide.with(mContext).load(url).override(500, 500).into(binding.imgCardHomeProduct)

        favList.forEach {
            if (it.productId == product.productId) {
                isFav = true
            }
        }


        if (isFav) {
            binding.imgCardHomeFav.setImageResource(
                mContext.resources.getIdentifier(
                    R.drawable.icon_fav.toString(),
                    "drawable",
                    mContext.packageName
                )
            )
        }

        binding.imgCardHomeFav.setOnClickListener {
            if (isFav) {
                viewModel.deleteFavorite(product.productId)
                binding.imgCardHomeFav.setImageResource(
                    mContext.resources.getIdentifier(
                        R.drawable.icon_not_fav.toString(),
                        "drawable",
                        mContext.packageName
                    )
                )

            } else {
                viewModel.addFavorite(
                    product.productId,
                    product.productName,
                    product.productImageName,
                    product.productPrice
                )
                binding.imgCardHomeFav.setImageResource(
                    mContext.resources.getIdentifier(
                        R.drawable.icon_fav.toString(),
                        "drawable",
                        mContext.packageName
                    )
                )
            }
            isFav = !isFav
        }

        binding.cardViewHomePage.setOnClickListener {
            val direction = HomePageFragmentDirections.homePageToDetail(product, isFav)
            Navigation.changePage(it, direction)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }
}