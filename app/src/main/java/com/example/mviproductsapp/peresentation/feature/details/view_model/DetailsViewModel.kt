package com.example.mviproductsapp.peresentation.feature.details.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mviproductsapp.domain.usecase.GetProductByIdUseCase
import com.example.mviproductsapp.peresentation.feature.details.DetailsIntent
import com.example.mviproductsapp.peresentation.feature.details.DetailsState
import com.example.mviproductsapp.utils.internet.InternetConnectivityViewModel
import com.example.productsapp.utils.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    val channelIntent = Channel<DetailsIntent>(Channel.UNLIMITED)

    private var _uiState: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState.Idle)
    val uiState = _uiState.asStateFlow()

    private var _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()
    val productId = saveStateHandle.toRoute<NavigationRoutes.DetailsScreen>().productId

    init {
        processIntent()
    }

    fun processIntent() {
        viewModelScope.launch {
            channelIntent.consumeAsFlow().collect {
                when (it) {
                    is DetailsIntent.GetProduct -> {
                        getProductById(it.isInternetConnected)
                    }
                }
            }
        }
    }

    fun sendIntent(intent: DetailsIntent) {
        viewModelScope.launch {
            channelIntent.send(intent)
        }
    }

    fun getProductById( isInternetConnected: Boolean) {
        viewModelScope.launch {
            try {
                _uiState.value = DetailsState.Loading
                    val result = getProductByIdUseCase.getProductById(productId, isInternetConnected)
                    _uiState.value = DetailsState.Success(result)
            } catch (e: Exception) {
                _uiState.value = DetailsState.Failure(e.message.toString())
                _message.emit(e.message.toString())
            }
        }
    }
}