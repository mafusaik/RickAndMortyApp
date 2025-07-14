package com.mafusaik.characters.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mafusaik.characters.data.api.RickAndMortyApi
import com.mafusaik.characters.data.api.mapper.toDomain
import com.mafusaik.characters.data.paging.CharacterRemoteMediator
import com.mafusaik.characters.domain.model.Character
import com.mafusaik.characters.domain.model.CharacterGender
import com.mafusaik.characters.domain.model.CharacterStatus
import com.mafusaik.characters.domain.repository.CharacterRepository
import com.mafusaik.database.RickAndMortyDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val dao: RickAndMortyDao
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(
        name: String?,
        status: CharacterStatus?,
        species: String?,
        type: String?,
        gender: CharacterGender?
    ): Flow<PagingData<Character>> {

        val remoteMediator = CharacterRemoteMediator(api, dao, name, status, species, type, gender)

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false,
                maxSize = 100
            ),
            initialKey = 1,
            remoteMediator = remoteMediator,
            pagingSourceFactory = { dao.getCharacters() }
        ).flow
            .map { pagingData ->
                pagingData.map { video ->
                    video.toDomain()
                }
            }
    }
}
