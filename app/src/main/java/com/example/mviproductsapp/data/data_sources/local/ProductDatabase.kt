package com.example.mviproductsapp.data.data_sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mviproductsapp.data.dto.ProductDto

@Database(entities = [ProductDto::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}