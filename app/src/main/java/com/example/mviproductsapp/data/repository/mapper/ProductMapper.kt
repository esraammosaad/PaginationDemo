package com.example.mviproductsapp.data.repository.mapper

import com.example.mviproductsapp.data.dto.ProductDto
import com.example.mviproductsapp.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title ?: "",
        description = description ?: "",
        category = category ?: "",
        price = price ?: 0.0,
        rating = rating ?: 0.0,
        stock = stock ?: 0,
        brand = brand ?: "",
        thumbnail = thumbnail ?: "",
    )
}

fun Product.toDto(): ProductDto{
    return ProductDto(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        rating = rating,
        stock = stock,
        brand = brand,
        thumbnail = thumbnail,
    )
}