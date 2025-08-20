package com.example.mviproductsapp.data.data_sources.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mviproductsapp.data.dto.ProductDto

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductDto>)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductDto>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductDto

    @Query("SELECT * FROM products ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, ProductDto>

    @Query("DELETE FROM products")
    suspend fun clearAll()
}