package com.example.foodorderapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.data.entity.CartProducts
import com.example.foodorderapp.databinding.CardViewCartBinding
import com.example.foodorderapp.ui.viewmodel.CartViewModel

class CartAdapter(
    var mContext: Context,
    var cartList: List<CartProducts>,
    var viewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CardViewHolder>() {

    inner class CardViewHolder(var design: CardViewCartBinding) :
        RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: CardViewCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_view_cart, parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cart = cartList.get(position)
        val binding = holder.design

        binding.cartObject = cart

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${cart.productImageName}"
        Glide.with(mContext).load(url).override(600,600).into(binding.imgCartProduct)


        binding.imgCartDelete.setOnClickListener {
            viewModel.incDecAction(
                "Delete",
                cart.cartProductId,
                cart.productName,
                cart.productImageName,
                cart.productPrice,
                cart.productQuantity,
                cart.username
            )
        }
        binding.imgCartDecrease.setOnClickListener {
            cart.productQuantity -= 1
            when(cart.productQuantity) {
                0 -> {
                    viewModel.incDecAction(
                        "Delete",
                        cart.cartProductId,
                        cart.productName,
                        cart.productImageName,
                        cart.productPrice,
                        0,
                        cart.username
                    )
                }
                else -> {
                    viewModel.incDecAction(
                        "Decrease",
                        cart.cartProductId,
                        cart.productName,
                        cart.productImageName,
                        cart.productPrice,
                        cart.productQuantity,
                        cart.username
                    )
                }
            }
        }
        binding.imgCartIncrease.setOnClickListener {
            cart.productQuantity += 1
            viewModel.incDecAction(
                "Increase",
                cart.cartProductId,
                cart.productName,
                cart.productImageName,
                cart.productPrice,
                cart.productQuantity,
                cart.username
            )
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}