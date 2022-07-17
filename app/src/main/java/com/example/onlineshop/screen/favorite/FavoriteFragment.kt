package com.example.onlineshop.screen.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentFavoriteBinding
import com.example.onlineshop.databinding.FragmentHomeBinding
import com.example.onlineshop.screen.MainViewModel
import com.example.onlineshop.utils.PrefUtils
import com.example.onlineshop.view.ProductAdapter

class FavoriteFragment : Fragment() {

    lateinit var binding: FragmentFavoriteBinding

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.productsData.observe(this, Observer {
            binding.recyclerProducts.adapter = ProductAdapter(it)
        })

        viewModel.error.observe(this, Observer{
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer{
            binding.swipe.isRefreshing = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerProducts.layoutManager = LinearLayoutManager(requireActivity())


        binding.swipe.setOnRefreshListener {
            loadData()
        }

        loadData()

    }

    fun loadData(){
        viewModel.getProductByIds(PrefUtils.getFavoritesList())
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}