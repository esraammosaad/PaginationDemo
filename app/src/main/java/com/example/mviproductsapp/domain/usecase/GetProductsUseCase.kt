package com.example.mviproductsapp.domain.usecase

import androidx.paging.PagingData
import com.example.mviproductsapp.domain.model.Product
import com.example.mviproductsapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val productRepository: ProductRepository) {
    fun getProducts(): Flow<PagingData<Product>> {
        return productRepository.getProducts()
    }
}