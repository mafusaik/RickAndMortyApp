package com.mafusaik.characters.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mafusaik.characters.domain.model.CharacterFilterType
import com.mafusaik.characters.domain.model.CharacterFilters
import com.mafusaik.characters.presentation.theme.GrayLight
import com.mafusaik.characters.presentation.ui.components.RoundTextField

@Composable
fun CharacterFiltersPanel(
    filters: CharacterFilters,
    onFilterChange: (CharacterFilterType, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(GrayLight.copy(alpha = 0.3f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FilterSection(
            title = "Status",
            options = listOf("alive", "dead", "unknown"),
            selected = filters.status?.value,
            onSelected = { onFilterChange(CharacterFilterType.STATUS, it) }
        )

        FilterSection(
            title = "Gender",
            options = listOf("female", "male", "genderless", "unknown"),
            selected = filters.gender?.value,
            onSelected = { onFilterChange(CharacterFilterType.GENDER, it) }
        )

        RoundTextField(
            value = filters.species,
            label = "Species",
            onValueChange = { onFilterChange(CharacterFilterType.SPECIES, it) },
        )

        RoundTextField(
            value = filters.type,
            label = "Type",
            onValueChange = { onFilterChange(CharacterFilterType.TYPE, it) },
        )
    }
}