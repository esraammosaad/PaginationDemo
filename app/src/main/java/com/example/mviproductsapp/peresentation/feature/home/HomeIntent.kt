package com.example.mviproductsapp.peresentation.feature.home

sealed class HomeIntent {
    data class ProductClicked(val productId: Int) : HomeIntent()
}