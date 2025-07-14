package com.mafusaik.characters.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.core.layout.WindowWidthSizeClass
import com.mafusaik.characters.R
import com.mafusaik.characters.domain.model.Character
import com.mafusaik.characters.domain.model.CharacterFilterType
import com.mafusaik.characters.domain.model.CharacterSearchState
import com.mafusaik.characters.presentation.ui.components.EmptyListMessage
import com.mafusaik.characters.presentation.ui.components.FullScreenProgress
import com.mafusaik.characters.presentation.ui.search.CustomizableSearchBar
import com.mafusaik.characters.presentation.viewmodel.CharactersViewModel

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onClick: (Character) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val columns = when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 2
        WindowWidthSizeClass.MEDIUM -> 3
        WindowWidthSizeClass.EXPANDED -> 4
        else -> 2
    }
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    CharactersScreen(
        topPadding = topPadding,
        state = state,
        searchQuery = searchQuery,
        columns = columns,
        onClick = onClick,
        onFilterChange = viewModel::onFilterChange,
        onResetFilters = viewModel::resetFilters,
        onQueryChange = {
            viewModel.onQueryChange(it)
        }
    )
}

@Composable
fun CharactersScreen(
    topPadding: Dp,
    state: CharacterSearchState,
    searchQuery: String,
    columns: Int,
    onClick: (Character) -> Unit,
    onFilterChange: (CharacterFilterType, String) -> Unit,
    onResetFilters: () -> Unit,
    onQueryChange: (String) -> Unit,
) {

    val characters = state.characters.collectAsLazyPagingItems()
    val loadState = characters.loadState
    val isRefreshing = loadState.refresh is LoadState.Loading
    var boxHeightPx by remember { mutableStateOf(0) }
    val boxHeightDp = with(LocalDensity.current) { boxHeightPx.toDp() }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            loadState.refresh is LoadState.Loading && characters.itemCount == 0 -> {
                FullScreenProgress()
            }

            loadState.refresh is LoadState.NotLoading && characters.itemCount == 0 -> {
                EmptyListMessage()
            }

            loadState.refresh is LoadState.Error -> {
                val error = (loadState.refresh as LoadState.Error).error
                ErrorScreen(
                    message = error.message ?: stringResource(R.string.error_generic),
                    onRetry = { characters.retry() }
                )
            }

            else -> {
                CharactersList(
                    topPadding = topPadding + boxHeightDp,
                    state = state,
                    columns = columns,
                    isRefreshing = isRefreshing,
                    onClick = onClick
                )
            }
        }

        CustomizableSearchBar(
            topPadding = topPadding,
            state = state,
            query = searchQuery,
            onQueryChange = onQueryChange,
            onFilterChange = onFilterChange,
            onResetFilters = onResetFilters,
            onContainerSize = {
                boxHeightPx = it
            }
        )
    }
}