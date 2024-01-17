package com.example.foodorderapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.FragmentCartBinding
import com.example.foodorderapp.ui.adapter.CartAdapter
import com.example.foodorderapp.ui.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var viewModel : CartViewModel
    private var username = "Salih"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)

        binding.cartFragment = this

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.loadCart(username)
        }


        viewModel.cartProductList.observe(viewLifecycleOwner) {
            val cartAdapter = CartAdapter(requireContext(),it,viewModel)
            binding.cartAdapter = cartAdapter
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.totalPrice = it
        }


        return binding.root
    }

    fun confirmCart() {
        Snackbar.make(binding.btnCartConfirm,"${binding.tvCartTotalPrice.text} tutarındaki sepeti onaylıyor musunuz?",Snackbar.LENGTH_SHORT)
            .setAction("Evet") {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.confirmCart(username)
                }
            }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : CartViewModel by viewModels()
        viewModel = tempViewModel

    }

}