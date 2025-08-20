package com.example.mviproductsapp.peresentation.feature.details.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mviproductsapp.R
import com.example.mviproductsapp.peresentation.common_view.CustomIdle
import com.example.mviproductsapp.peresentation.common_view.CustomLoading
import com.example.mviproductsapp.peresentation.feature.details.DetailsIntent
import com.example.mviproductsapp.peresentation.feature.details.DetailsState
import com.example.mviproductsapp.peresentation.feature.details.view.components.CustomProductDetails
import com.example.mviproductsapp.peresentation.feature.details.view_model.DetailsViewModel
import com.example.productsapp.utils.view.CustomError
import kotlinx.coroutines.flow.StateFlow


@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel,
    isInternetConnected: StateFlow<Boolean?>,
    onBackClick: () -> Unit
) {

    val uiState = detailsViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val isInternetConnected =
        isInternetConnected.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        detailsViewModel.sendIntent(DetailsIntent.GetProduct(isInternetConnected.value ?: false))
        detailsViewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp, horizontal = 16.dp)
    ) {
        Row {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.back_icon),
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        onBackClick()
                    }
            )
        }
        when (uiState.value) {
            DetailsState.Idle -> {
                CustomIdle()
            }

            is DetailsState.Loading -> {
                CustomLoading()
            }

            is DetailsState.Failure -> {
                val error = (uiState.value as DetailsState.Failure).message
                CustomError(error)
            }

            is DetailsState.Success -> {
                CustomProductDetails(uiState.value)
            }
        }
    }
}

