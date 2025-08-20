package com.example.mviproductsapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mviproductsapp.data.data_sources.remote.ProductRemoteMediator
import com.example.mviproductsapp.data.dto.ProductDto
import com.example.mviproductsapp.data.repository.mapper.toDomain
import com.example.mviproductsapp.domain.model.Product
import com.example.mviproductsapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val pager : Pager<Int, ProductDto>
) :
    ProductRepository {
    override fun getProducts(): Flow<PagingData<Product>> {
       return pager.flow.map { pagingData -> pagingData.map {
           it.toDomain()
       } }
    }

    override suspend fun getProductById(id: Int): Product {
        return remoteDataSource.getProductById(id).toDomain()
    }

    override suspend fun getProductByIdFromDB(id: Int): Product {
        return localDataSource.getProductById(id).toDomain()
    }
}