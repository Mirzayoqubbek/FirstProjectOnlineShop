package com.example.onlineshop.utils

import com.example.onlineshop.model.CartModel
import com.example.onlineshop.model.ProductModel
import com.orhanobut.hawk.Hawk

object PrefUtils {
    const val PREF_FAVORITES = "pref_favorites"
    const val PREF_CARD = "pref_card"

    fun setFavorites(item: ProductModel){
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())

        if (items.filter { it == item.id }.firstOrNull() != null){
            items.remove(item.id)
        } else {
            items.add(item.id)
        }

        Hawk.put(PREF_FAVORITES, items)
    }

    fun getFavoritesList(): ArrayList<Int>{
        return Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
    }

    fun checkFavorite(item: ProductModel): Boolean{
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }

    fun setCart(item: ProductModel){
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CARD, arrayListOf<CartModel>())
        val cart = items.filter { it.product_id == item.id }.firstOrNull()
        if (cart != null){
            if (item.cart_count > 0){
                cart.count = item.cart_count
            } else {
                items.remove(cart)
            }
        } else {
            val newCart = CartModel(item.id, item.cart_count)
            items.add(newCart)
        }

        Hawk.put(PREF_CARD, items)
    }

    fun getCartList(): ArrayList<CartModel>{
        return Hawk.get(PREF_CARD, arrayListOf<CartModel>())
    }

    fun gerCartCount(item: ProductModel): Int{
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CARD, arrayListOf<CartModel>())
        return items.filter { it.product_id == item.id }.firstOrNull()?.count ?: 0
    }
}