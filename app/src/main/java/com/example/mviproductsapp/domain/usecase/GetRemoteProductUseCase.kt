package com.example.mviproductsapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mviproductsapp.data.data_sources.remote.RemotePagingSource
import com.example.mviproductsapp.data.repository.RemoteDataSource
import com.example.mviproductsapp.data.repository.mapper.toDomain
import com.example.mviproductsapp.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRemoteProductUseCase(
    private val remoteDataSource: RemoteDataSource
) {

    fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RemotePagingSource(remoteDataSource) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}