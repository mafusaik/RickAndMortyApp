package com.mafusaik.characters.domain.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class CharacterSearchState(
    val characters: Flow<PagingData<Character>> = flow { PagingData.empty<Character>() },
    val query: String? = null,
    val filters: CharacterFilters = CharacterFilters(),
//    val isLoading: Boolean = false,
//    val error: String? = null
)