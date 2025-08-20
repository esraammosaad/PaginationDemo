package com.example.mviproductsapp.data.repository

import androidx.paging.PagingSource
import com.example.mviproductsapp.data.dto.ProductDto

interface LocalDataSource {
    suspend fun insertProducts(products: List<ProductDto>)
    fun pagingSource(): PagingSource<Int, ProductDto>
    suspend fun getAllProducts(): List<ProductDto>
    suspend fun getProductById(id: Int): ProductDto
    suspend fun clearAll()
}