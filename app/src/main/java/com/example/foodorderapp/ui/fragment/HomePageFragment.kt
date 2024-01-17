package com.example.foodorderapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.foodorderapp.R
import com.example.foodorderapp.data.entity.FavoriteProducts
import com.example.foodorderapp.data.entity.Products
import com.example.foodorderapp.databinding.FragmentHomePageBinding
import com.example.foodorderapp.ui.adapter.ProductsAdapter
import com.example.foodorderapp.ui.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private lateinit var binding : FragmentHomePageBinding
    private lateinit var viewModel : HomePageViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_page,container,false)

        //Adapter
        viewModel.productsList.observe(viewLifecycleOwner) {
            binding.productAdapter = ProductsAdapter(
               requireContext(),
                it["Products"] as List<Products>,
                it["Favorites"] as List<FavoriteProducts>,
                viewModel
            )
            binding.rvHomePage.setItemViewCacheSize(it["Products"]!!.size)
        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : HomePageViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.productAdapter = null
        viewModel.loadProducts()
    }


}