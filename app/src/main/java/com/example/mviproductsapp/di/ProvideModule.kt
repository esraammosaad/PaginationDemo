package com.example.mviproductsapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.mviproductsapp.data.data_sources.local.ProductDao
import com.example.mviproductsapp.data.data_sources.local.ProductDatabase
import com.example.mviproductsapp.data.data_sources.remote.ApiService
import com.example.mviproductsapp.data.data_sources.remote.ProductRemoteMediator
import com.example.mviproductsapp.data.data_sources.remote.RetrofitFactory
import com.example.mviproductsapp.data.dto.ProductDto
import com.example.mviproductsapp.data.repository.LocalDataSource
import com.example.mviproductsapp.data.repository.RemoteDataSource
import com.example.mviproductsapp.domain.repository.ProductRepository
import com.example.mviproductsapp.domain.usecase.GetProductByIdUseCase
import com.example.mviproductsapp.domain.usecase.GetProductsUseCase
import com.example.mviproductsapp.domain.usecase.GetRemoteProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        RetrofitFactory.apiService

    @Provides
    @Singleton
    fun provideProductDao(@ApplicationContext context: Context): ProductDao =
        Room.databaseBuilder(context, ProductDatabase::class.java, "Product_Database")
            .build().getProductDao()


    @Provides
    fun provideGetRemoteProductUseCase(remoteDataSourceImpl: RemoteDataSource): GetRemoteProductUseCase =
        GetRemoteProductUseCase( remoteDataSourceImpl)

    @Provides
    fun provideGetProductUseCase(productRepositoryImpl: ProductRepository): GetProductsUseCase =
        GetProductsUseCase(productRepositoryImpl)

    @Provides
    fun provideGetProductByIdUseCase(productRepositoryImpl: ProductRepository): GetProductByIdUseCase =
        GetProductByIdUseCase(productRepositoryImpl)


    @Provides
    @Singleton
    fun provideProductPager(
        localDataSourceImpl: LocalDataSource,
        remoteDataSourceImpl: RemoteDataSource
    ): Pager<Int, ProductDto> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
            ),
            remoteMediator = ProductRemoteMediator(
                localDataSource = localDataSourceImpl,
                remoteDataSource = remoteDataSourceImpl
            ),
            pagingSourceFactory = {
                localDataSourceImpl.pagingSource()
            }
        )
    }
}