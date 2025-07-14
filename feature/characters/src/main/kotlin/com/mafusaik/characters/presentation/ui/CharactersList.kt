package com.mafusaik.characters.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mafusaik.characters.domain.model.Character
import com.mafusaik.characters.domain.model.CharacterSearchState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersList(
    topPadding: Dp,
    state: CharacterSearchState,
    columns: Int,
    isRefreshing: Boolean,
    onClick: (Character) -> Unit
) {

    val characters = state.characters.collectAsLazyPagingItems()
    val listState = rememberLazyGridState()
    Log.i("PAGING", "recomposition CharactersScreen ${characters.itemCount}")

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            characters.refresh()
        },
    ) {
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(columns),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(columns) {
                Spacer(Modifier.height(topPadding))
            }
            items(
                characters.itemCount,
                key = characters.itemKey { it.id }
            ) {
                characters[it]?.let { characterModel ->
                    CharacterItem(
                        Modifier.animateItem(),
                        characterModel,
                        onClick
                    )
                }
            }
        }
    }
}