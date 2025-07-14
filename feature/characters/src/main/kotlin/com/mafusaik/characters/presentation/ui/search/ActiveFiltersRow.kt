package com.mafusaik.characters.presentation.ui.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mafusaik.characters.domain.model.CharacterFilterType
import com.mafusaik.characters.domain.model.CharacterFilters

@Composable
fun ActiveFiltersRow(
    filters: CharacterFilters,
    onFilterRemove: (CharacterFilterType, String) -> Unit,
    onResetAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.getActiveFilters().forEach { (type, value) ->
            InputChip(
                selected = true,
                onClick = { onFilterRemove(type, "") },
                label = { Text("${type.title}: $value") },
                trailingIcon = {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove filter"
                    )
                }
            )
        }

        Spacer(Modifier.weight(1f))

        TextButton(onClick = onResetAll) {
            Text("Reset all")
        }
    }
}