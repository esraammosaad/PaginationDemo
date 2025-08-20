package com.example.mviproductsapp.peresentation.feature.home

sealed class HomeEvent {
    data class NavigateToDetails(val productId : Int) : HomeEvent()
    data class ShowToast(val message : String) : HomeEvent()
}