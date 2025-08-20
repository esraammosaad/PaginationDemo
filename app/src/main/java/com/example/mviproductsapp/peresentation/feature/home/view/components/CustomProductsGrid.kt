package com.example.mviproductsapp.peresentation.feature.home.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.mviproductsapp.R
import com.example.mviproductsapp.domain.model.Product
import com.example.productsapp.utils.Styles
import com.example.mviproductsapp.peresentation.common_view.CustomLoading

@Composable
fun CustomProductsGrid(
    uiState: LazyPagingItems<Product>,
    onCardClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            stringResource(R.string.products),
            style = Styles.testStyle28
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            stringResource(R.string.products_found, uiState.itemCount),
            style = Styles.testStyle14
        )
        Spacer(modifier = Modifier.height(18.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState, key = { it.id }) { product ->
                product.let {
                    CustomProductItem(
                        product = it,
                        onCardClick = { onCardClick(it.id) }
                    )
                }
            }
            if (uiState.loadState.append is LoadState.Loading) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomLoading()
                    }
                }
            }
        }
    }
}

fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    itemContent: @Composable (T) -> Unit
) {
    items(count = items.itemCount) { index ->
        val item = items[index]
        if (item != null) {
            if (key != null) {
                key(key(item)) {
                    itemContent(item)
                }
            } else {
                itemContent(item)
            }
        }
    }
}


