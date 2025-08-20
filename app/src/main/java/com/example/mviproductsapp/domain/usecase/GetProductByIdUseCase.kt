package com.example.mviproductsapp.domain.usecase

import com.example.mviproductsapp.domain.model.Product
import com.example.mviproductsapp.domain.repository.ProductRepository

class GetProductByIdUseCase(
    private val productRepository: ProductRepository,
) {
    suspend fun getProductById(id: Int, isInternetConnected: Boolean): Product {
        return if (isInternetConnected) productRepository.getProductById(id) else productRepository.getProductByIdFromDB(
            id
        )
    }
}