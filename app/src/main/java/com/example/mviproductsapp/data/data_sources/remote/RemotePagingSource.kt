package com.example.mviproductsapp.data.data_sources.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mviproductsapp.data.dto.ProductDto
import com.example.mviproductsapp.data.repository.RemoteDataSource

class RemotePagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, ProductDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {
        return try {
            val page = params.key ?: 0
            val skip = page * params.loadSize


            val response = remoteDataSource.getProducts(
                limit = params.loadSize,
                skip = skip
            )

            val products = response.products ?: emptyList()


            LoadResult.Page(
                data = products,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (products.size < params.loadSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}