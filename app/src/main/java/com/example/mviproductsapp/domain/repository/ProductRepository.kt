package com.example.mviproductsapp.domain.repository

import androidx.paging.PagingData
import com.example.mviproductsapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<PagingData<Product>>
    suspend fun getProductById(id: Int): Product
    suspend fun getProductByIdFromDB(id: Int): Product
}