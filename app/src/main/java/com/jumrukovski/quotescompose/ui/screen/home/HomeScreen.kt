package com.jumrukovski.quotescompose.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jumrukovski.quotescompose.R
import com.jumrukovski.quotescompose.domain.model.MenuItem
import com.jumrukovski.quotescompose.domain.model.Quote
import com.jumrukovski.quotescompose.ui.common.component.EmptyDataCard
import com.jumrukovski.quotescompose.ui.common.component.ProgressBar
import com.jumrukovski.quotescompose.ui.common.component.SmallQuoteCard
import com.jumrukovski.quotescompose.ui.common.component.TopBar
import com.jumrukovski.quotescompose.ui.common.state.UIState
import com.jumrukovski.quotescompose.ui.theme.PrimaryBackgroundColor

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToQuoteDetails: (Quote) -> Unit,
    onNavigateToRandomQuote: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getQuotes()
    }

    Column {
        TopBar(
            title = stringResource(id = R.string.screen_home),
            menuItems = listOf(
                MenuItem(
                    R.string.action_random,
                    R.drawable.baseline_random,
                    0,
                    false
                )
            ),
            onMenuItemClick = {
                if (it.titleTextId == R.string.action_random) {
                    onNavigateToRandomQuote()
                }
            }
        )
        Contents(paddingValues = PaddingValues(16.dp), state = uiState, onItemClicked = {
            onNavigateToQuoteDetails(it)
        })
    }
}

@Composable
private fun Contents(
    paddingValues: PaddingValues,
    state: UIState<List<Quote>>,
    onItemClicked: (Quote) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackgroundColor)
            .padding(paddingValues)
    ) {
        when (state) {
            is UIState.Error -> EmptyDataCard(reason = stringResource(id = R.string.error))
            is UIState.Exception -> EmptyDataCard(reason = stringResource(id = R.string.error))
            is UIState.Loading -> ProgressBar()
            UIState.SuccessWithNoData -> EmptyDataCard(
                reason = stringResource(id = R.string.no_data)
            )
            is UIState.SuccessWithData -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(state.data) { quote ->
                        SmallQuoteCard(
                            quote = quote,
                            onNavigateToQuoteDetails = {
                                onItemClicked(it)
                            }
                        )
                    }
                }
            }
        }
    }
}
