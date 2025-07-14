package com.mafusaik.characters.domain.repository

import androidx.paging.PagingData
import com.mafusaik.characters.domain.model.Character
import com.mafusaik.characters.domain.model.CharacterGender
import com.mafusaik.characters.domain.model.CharacterStatus
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(
        name: String?,
        status: CharacterStatus?,
        species: String?,
        type: String?,
        gender: CharacterGender?
    ): Flow<PagingData<Character>>
}
