package com.example.foodorderapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.data.entity.FavoriteProducts
import com.example.foodorderapp.databinding.CardViewFavBinding
import com.example.foodorderapp.databinding.FragmentFavoritesBinding
import com.example.foodorderapp.ui.viewmodel.FavoritesViewModel

class FavoritesAdapter(
    var mContext: Context,
    var favList: List<FavoriteProducts>,
    var viewModel: FavoritesViewModel,
    var username: String
) : RecyclerView.Adapter<FavoritesAdapter.CardViewHolder>() {

    inner class CardViewHolder(var binding: CardViewFavBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: CardViewFavBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_view_fav,
            parent,
            false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val fav = favList.get(position)
        val binding = holder.binding

        binding.favObject = fav

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${fav.productImageName}"
        Glide.with(mContext).load(url).override(600, 600).into(binding.imgFavProduct)

        binding.btnFavAddCart.setOnClickListener {
            viewModel.addToCart(
                fav.productName,
                fav.productImageName,
                fav.productPrice,
                1,
                username
            )
        }
        binding.fabFav.setOnClickListener {
            viewModel.deleteFav(fav.productId)
        }
    }

    override fun getItemCount(): Int {
        return favList.size
    }
}