package com.example.mviproductsapp.peresentation.feature.details
sealed class DetailsIntent {
    data class GetProduct(val isInternetConnected: Boolean) : DetailsIntent()
}