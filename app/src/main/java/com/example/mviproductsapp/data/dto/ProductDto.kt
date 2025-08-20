package com.example.mviproductsapp.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductDto(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val description: String?,
    val category: String?,
    val price: Double?,
    val rating: Double?,
    val stock: Int?,
    val brand: String?,
    val thumbnail: String?,
)