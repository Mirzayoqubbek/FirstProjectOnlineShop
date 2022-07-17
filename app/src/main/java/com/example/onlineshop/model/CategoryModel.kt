package com.example.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categries")
data class CategoryModel(
        @PrimaryKey(autoGenerate = true)
        val uid: Int,
        val id: Int,
        val title: String,
        val icon: String,
        var checked: Boolean = false
)