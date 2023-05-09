package com.jumrukovski.quotescompose.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jumrukovski.quotescompose.R
import com.jumrukovski.quotescompose.data.model.MenuItem
import com.jumrukovski.quotescompose.data.model.middleware.Quote
import com.jumrukovski.quotescompose.ui.common.component.LargeQuoteCard
import com.jumrukovski.quotescompose.ui.common.component.TopBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun QuoteDetailScreen(
    viewModel: QuoteDetailViewModel,
    id: String,
    content: String,
    author: String,
    onNavigateBack: () -> Unit
) {
    var isFavourite by remember { mutableStateOf<Quote?>(null) }

    var menuItems by remember {
        mutableStateOf(
            listOf(
                MenuItem(
                    R.string.action_favourite,
                    R.drawable.baseline_favorite_border_24,
                    R.drawable.baseline_favorite_24,
                    false
                )
            )
        )
    }

    LaunchedEffect(key1 = id, block = {
        viewModel.checkIfQuoteIsInFavouritesDB(id).collectLatest {
            isFavourite = it

            menuItems = menuItems.map { item ->
                if (item.titleTextId == R.string.action_favourite)
                    item.copy(isSelected = it != null)
                else
                    item
            }
        }
    })

    Column {
        TopBar(
            isBackButtonEnabled = true,
            onNavigateBack = onNavigateBack,
            menuItems = menuItems,
            onMenuItemClick = {
                if (it.titleTextId == R.string.action_favourite) {
                    if (isFavourite == null) {
                        viewModel.addQuoteToFavourites(id, content, author)
                    } else {
                        viewModel.removeQuoteFromFavourites(id, content, author)
                    }
                }
            }
        )
        Contents(paddingValues = PaddingValues(), content, author)
    }
}

@Composable
private fun Contents(paddingValues: PaddingValues, content: String, author: String) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        LargeQuoteCard(content, author)
    }
}