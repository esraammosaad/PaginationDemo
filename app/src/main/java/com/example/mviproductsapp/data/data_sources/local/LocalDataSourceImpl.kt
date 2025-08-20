package com.example.mviproductsapp.data.data_sources.local

import androidx.paging.PagingSource
import com.example.mviproductsapp.data.dto.ProductDto
import com.example.mviproductsapp.data.repository.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val productDao: ProductDao) :
    LocalDataSource {
    override suspend fun insertProducts(products: List<ProductDto>) {
        productDao.insertProducts(products)
    }

    override fun pagingSource(): PagingSource<Int, ProductDto> {
        return productDao.pagingSource()
    }

    override suspend fun getAllProducts(): List<ProductDto> {
        return productDao.getAllProducts()
    }

    override suspend fun getProductById(id: Int): ProductDto {
       return productDao.getProductById(id)
    }

    override suspend fun clearAll() {
        productDao.clearAll()
    }
}