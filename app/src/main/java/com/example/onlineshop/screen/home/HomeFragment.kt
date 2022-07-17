package com.example.onlineshop.screen.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.onlineshop.view.ProductAdapter
import com.example.onlineshop.view.SliderAdapter
import com.example.onlineshop.databinding.FragmentHomeBinding
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.model.SliderItem
import com.example.onlineshop.screen.MainViewModel
import com.example.onlineshop.view.CategoryAdapter
import com.example.onlineshop.view.CategoryAdapterCallback
import java.lang.Math.abs

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    lateinit var viewModel: MainViewModel

    var sliderHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.recyclerCategories.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerProducts.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = it
        })

        viewModel.offersData.observe(viewLifecycleOwner, Observer{
            binding.viewPagerImageSlider.adapter = SliderAdapter(it.map { SliderItem(it.image) }.toMutableList(),  binding.viewPagerImageSlider)
        })

        binding.viewPagerImageSlider.clipToPadding = false

        binding.viewPagerImageSlider.clipChildren = false
        binding.viewPagerImageSlider.offscreenPageLimit = 3
        binding.viewPagerImageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(0))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.00f + r * 1.00f
        }
        binding.viewPagerImageSlider.setPageTransformer(compositePageTransformer)
        binding.viewPagerImageSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })

        viewModel.categoriesData.observe(viewLifecycleOwner, Observer {
            binding.recyclerCategories.adapter = CategoryAdapter(it, object: CategoryAdapterCallback{
                override fun onClincItem(item: CategoryModel) {
                    viewModel.getProductByCategory(item.id)
                }

            })
        })

        viewModel.productsData.observe(viewLifecycleOwner, Observer {
            binding.recyclerProducts.adapter = ProductAdapter(it)
        })

        loadData()

    }

    fun loadData(){
        viewModel.getOffers()
        viewModel.getAllDBCategorry()
        viewModel.getAllDBProducts()
//        viewModel.getTopProducts()
    }

    private val sliderRunnable = Runnable {
        binding.viewPagerImageSlider.currentItem =  binding.viewPagerImageSlider.currentItem + 1
    }

    override fun onDestroy() {
        sliderHandler.removeCallbacks(sliderRunnable)
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}

