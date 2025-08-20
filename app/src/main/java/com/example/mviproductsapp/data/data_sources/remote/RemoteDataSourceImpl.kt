package com.example.mviproductsapp.data.data_sources.remote

import com.example.mviproductsapp.data.dto.ProductDto
import com.example.mviproductsapp.data.dto.ProductResponseDto
import com.example.mviproductsapp.data.repository.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun getProducts(limit: Int, skip: Int): ProductResponseDto {
        return apiService.getProducts(limit = limit, skip = skip)
    }

    override suspend fun getProductById(id: Int): ProductDto {
        return apiService.getProductById(id)
    }
}
