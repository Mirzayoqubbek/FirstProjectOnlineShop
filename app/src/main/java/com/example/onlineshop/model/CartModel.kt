package com.example.onlineshop.model

import java.io.Serializable

data class CartModel(
    val product_id: Int,
    var count: Int
): Serializable