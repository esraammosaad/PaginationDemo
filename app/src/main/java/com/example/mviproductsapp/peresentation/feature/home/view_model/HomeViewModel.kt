package com.example.mviproductsapp.peresentation.feature.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mviproductsapp.domain.usecase.GetProductsUseCase
import com.example.mviproductsapp.peresentation.feature.home.HomeEvent
import com.example.mviproductsapp.peresentation.feature.home.HomeIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    getProductUseCase: GetProductsUseCase
) : ViewModel() {
    val channelIntent = Channel<HomeIntent>(Channel.UNLIMITED)
    val productPagingFlow = getProductUseCase.getProducts().cachedIn(viewModelScope)
    private var _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {
        processIntent()
    }

    private fun processIntent() {
        viewModelScope.launch {
            channelIntent.consumeAsFlow().collect {
                when (it) {
                    is HomeIntent.ProductClicked -> {
                        _event.emit(HomeEvent.NavigateToDetails(it.productId))
                    }
                }
            }
        }
    }

    fun sendIntent(intent: HomeIntent) {
        viewModelScope.launch {
            channelIntent.send(intent)
        }
    }
}