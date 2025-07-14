package com.mafusaik.characters.presentation.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mafusaik.characters.domain.model.CharacterFilterType
import com.mafusaik.characters.domain.model.CharacterSearchState
import com.mafusaik.characters.presentation.theme.White
import com.mafusaik.characters.presentation.ui.components.RoundTextField

@Composable
fun CustomizableSearchBar(
    topPadding: Dp,
    state: CharacterSearchState,
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterChange: (CharacterFilterType, String) -> Unit,
    onResetFilters: () -> Unit,
    onContainerSize: (Int) -> Unit,
) {
    var filtersExpanded by remember { mutableStateOf(false) }
    val filters = state.filters

    Surface(
        modifier = Modifier
            .padding(top = topPadding)
            .onSizeChanged { size ->
                onContainerSize(size.height)
            },
        color = White.copy(alpha = 0.8f)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)

            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    RoundTextField(
                        value = query,
                        label = "Search name",
                        onValueChange = onQueryChange,
                    )
                }

                IconButton(
                    onClick = { filtersExpanded = !filtersExpanded },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    BadgedBox(
                        badge = {
                            if (filters.hasActiveFilters()) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Filters"
                        )
                    }
                }
            }

            if (filters.hasActiveFilters()) {
                ActiveFiltersRow(
                    filters = filters,
                    onFilterRemove = onFilterChange,
                    onResetAll = onResetFilters,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            AnimatedVisibility(
                visible = filtersExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                CharacterFiltersPanel(
                    filters = filters,
                    onFilterChange = onFilterChange,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}