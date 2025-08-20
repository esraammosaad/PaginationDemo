package com.example.mviproductsapp.data.repository

import com.example.mviproductsapp.data.dto.ProductDto
import com.example.mviproductsapp.data.dto.ProductResponseDto

interface RemoteDataSource {
    suspend fun getProducts(limit: Int, skip: Int): ProductResponseDto
    suspend fun getProductById(id: Int): ProductDto
}