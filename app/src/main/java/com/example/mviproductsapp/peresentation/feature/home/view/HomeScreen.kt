package com.example.mviproductsapp.peresentation.feature.home.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mviproductsapp.R
import com.example.mviproductsapp.peresentation.feature.home.HomeEvent
import com.example.mviproductsapp.peresentation.feature.home.HomeIntent.ProductClicked
import com.example.mviproductsapp.peresentation.feature.home.view.components.CustomProductsGrid
import com.example.mviproductsapp.peresentation.feature.home.view_model.HomeViewModel
import com.example.mviproductsapp.ui.theme.Green
import com.example.mviproductsapp.ui.theme.Red
import com.example.mviproductsapp.peresentation.common_view.TopSnackBar
import com.example.productsapp.utils.NavigationRoutes
import com.example.mviproductsapp.peresentation.common_view.CustomLoading
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navigationController: NavHostController,
    isInternetConnected: StateFlow<Boolean?>
) {
    val context = LocalContext.current
    val uiState = homeViewModel.productPagingFlow.collectAsLazyPagingItems()
    val isInternetConnectedValue = isInternetConnected.collectAsStateWithLifecycle()
    var snackBarData by remember { mutableStateOf<SnackBarData?>(null) }
    val isRefreshing = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        homeViewModel.event.collect {
            when (it) {
                is HomeEvent.NavigateToDetails -> {
                    navigationController.navigate(NavigationRoutes.DetailsScreen(it.productId))
                }

                is HomeEvent.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(isInternetConnectedValue.value) {
        when (isInternetConnectedValue.value) {
            true -> {
                uiState.retry()
                snackBarData = SnackBarData(
                    message = context.getString(R.string.internet_connected),
                    color = Green
                )
            }

            false -> {
                snackBarData = SnackBarData(
                    message = context.getString(R.string.no_internet_connection),
                    color = Red
                )
            }

            null -> {}
        }
    }


    Column {
        snackBarData?.let { data ->
            TopSnackBar(
                message = data.message,
                color = data.color,
                onDismiss = { snackBarData = null }
            )
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = {
                uiState.refresh()
                isRefreshing.value = true
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 50.dp, horizontal = 16.dp)
        ) {
            when (uiState.loadState.refresh) {
                is LoadState.Loading -> CustomLoading()
                else -> {
                    isRefreshing.value = false
                    CustomProductsGrid(uiState) {
                        homeViewModel.sendIntent(ProductClicked(it))
                    }
                }
            }
        }
    }
}

data class SnackBarData(
    val message: String,
    val color: Color
)
