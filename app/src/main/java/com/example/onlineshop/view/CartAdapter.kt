package com.example.onlineshop.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.databinding.CartItemLayoutBinding
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.utils.Constants

class cartAdapter(val items: List<ProductModel>): RecyclerView.Adapter<cartAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: CartItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.tvPrice.text = item.price
        holder.binding.tvName.text = item.name
        Glide.with(holder.binding.imgProduct).load(Constants.HOST_IMAGE + item.image).into(holder.binding.imgProduct)

    }
    override fun getItemCount(): Int = items.count()
}