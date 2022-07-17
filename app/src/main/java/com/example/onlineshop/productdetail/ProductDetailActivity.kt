package com.example.onlineshop.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ActivityMainBinding
import com.example.onlineshop.databinding.ActivityProductDetailBinding
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.utils.PrefUtils

class ProductDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailBinding

    lateinit var item: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Back.setOnClickListener{
            finish()
        }

        binding.Favorite.setOnClickListener {
            PrefUtils.setFavorites(item)

            if (PrefUtils.checkFavorite(item)){
                binding.ImgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.ImgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        item = intent.getSerializableExtra(Constants.EXTR_DATA) as ProductModel

        binding.TvTitle.text = item.name
        binding.TvProductName.text = item.name
        binding.TvPrise.text = item.price

        if (PrefUtils.gerCartCount(item) > 0){
            binding.btnAdd2cart.visibility = View.GONE
        }

        if (PrefUtils.checkFavorite(item)){
            binding.ImgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.ImgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        Glide.with(this).load(Constants.HOST_IMAGE + item.image).into(binding.ImgProduct)

        binding.btnAdd2cart.setOnClickListener {
            item.cart_count = 1
            PrefUtils.setCart(item)
            Toast.makeText(this, "Mahsulot karzinkaga qo'shildi!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}