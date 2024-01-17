package com.example.foodorderapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.FragmentFavoritesBinding
import com.example.foodorderapp.ui.adapter.FavoritesAdapter
import com.example.foodorderapp.ui.viewmodel.FavoritesViewModel
import com.example.foodorderapp.ui.viewmodel.HomePageViewModel
import com.example.foodorderapp.utils.changePage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var viewModel : FavoritesViewModel
    private lateinit var binding : FragmentFavoritesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorites,container,false)

        viewModel.favoriteList.observe(viewLifecycleOwner) {
            val favAdapter = FavoritesAdapter(requireContext(),it,viewModel,"Salih")
            binding.favAdapter = favAdapter
        }


        CoroutineScope(Dispatchers.Main).launch {
            viewModel.loadFav()
        }





        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : FavoritesViewModel by viewModels()
        viewModel = tempViewModel
    }
}