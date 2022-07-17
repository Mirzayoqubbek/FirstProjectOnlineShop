package com.example.onlineshop.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.databinding.ProductItemLayoutBinding
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.productdetail.ProductDetailActivity
import com.example.onlineshop.utils.Constants

class ProductAdapter(val items: List<ProductModel>?): RecyclerView.Adapter<ProductAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: ProductItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items?.get(position)
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra(Constants.EXTR_DATA, item)
            it.context.startActivity(intent)
        }
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item?.image).into(holder.binding.imgPraduct)
        holder.binding.tvName.text = item?.name
        holder.binding.tvPrice.text = item?.price
    }

    override fun getItemCount(): Int {
        return items!!.count()
    }
}