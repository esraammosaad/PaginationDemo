package com.example.mviproductsapp.di

import com.example.mviproductsapp.data.data_sources.local.LocalDataSourceImpl
import com.example.mviproductsapp.data.data_sources.remote.RemoteDataSourceImpl
import com.example.mviproductsapp.data.repository.LocalDataSource
import com.example.mviproductsapp.data.repository.ProductRepositoryImpl
import com.example.mviproductsapp.data.repository.RemoteDataSource
import com.example.mviproductsapp.domain.repository.ProductRepository
import com.example.mviproductsapp.utils.internet.InternetObserver
import com.example.mviproductsapp.utils.internet.InternetObserverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindInternetObserver(internetObserverImpl: InternetObserverImpl): InternetObserver
}