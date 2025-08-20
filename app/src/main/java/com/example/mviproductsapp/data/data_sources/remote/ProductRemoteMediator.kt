package com.example.mviproductsapp.data.data_sources.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import coil.network.HttpException
import com.example.mviproductsapp.data.dto.ProductDto
import com.example.mviproductsapp.data.repository.LocalDataSource
import com.example.mviproductsapp.data.repository.RemoteDataSource
import okio.IOException

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, ProductDto>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductDto>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        0
                    }
                    lastItem?.id ?: 0
                }
            }
//            if (loadType == LoadType.REFRESH) {
//                val cached = localDataSource.getAllProducts()
//                if (cached.isNotEmpty()) {
//                    return MediatorResult.Success(endOfPaginationReached = false)
//                }
//            }
            val products: List<ProductDto> =
                remoteDataSource.getProducts(limit = state.config.pageSize, skip = loadKey).products
                    ?: emptyList()

            if (loadType == LoadType.REFRESH) {
                localDataSource.clearAll()
            }
            localDataSource.insertProducts(products)
            MediatorResult.Success(endOfPaginationReached = products.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}