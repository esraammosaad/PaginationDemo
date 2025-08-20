package com.example.mviproductsapp.utils.internet

import kotlinx.coroutines.flow.Flow

interface InternetObserver {
    val isConnected : Flow<Boolean>
}