package com.mafusaik.characters.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mafusaik.characters.data.api.RickAndMortyApi
import com.mafusaik.characters.data.api.mapper.toEntity
import com.mafusaik.characters.domain.model.CharacterGender
import com.mafusaik.characters.domain.model.CharacterStatus
import model.CharacterEntity
import com.mafusaik.database.RickAndMortyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val api: RickAndMortyApi,
    private val dao: RickAndMortyDao,
    private val name: String?,
    private val status: CharacterStatus?,
    private val species: String?,
    private val type: String?,
    private val gender: CharacterGender?
    ) : RemoteMediator<Int, CharacterEntity>() {

    private var page = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        when (loadType) {
            LoadType.REFRESH -> {
                page = 0
                Log.i("VIDEO_PAGING", "REFRESH")
            }

            LoadType.PREPEND -> {
                Log.i("VIDEO_PAGING", "PREPEND $page")
                if (page == 0) return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                page -= 1
                return MediatorResult.Success(endOfPaginationReached = false)
            }

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                Log.i("VIDEO_PAGING", "APPEND lastItem $lastItem  ${state.anchorPosition}")
                if (lastItem == null || state.anchorPosition == 0)
                    return MediatorResult.Success(endOfPaginationReached = false)

                page += 1
            }
        }

        val limit = state.config.pageSize

        val result = withContext(Dispatchers.IO) {
            executeRequest {
                api.getCharacters(
                    page = page,
                    name = name,
                    status = status?.value,
                    species = species,
                    type = type,
                    gender = gender?.value
                )
            }
        }

        return result.fold(
            onSuccess = { body ->
                val videos = body.results

                val entities = videos.map { it.toEntity() }

                withContext(Dispatchers.IO) {
                    if (entities.isNotEmpty() && loadType == LoadType.REFRESH) {
                        dao.clearAll()
                        Log.i("VIDEO_PAGING", "videoDao.clearAll")
                    }
                    dao.insertAll(entities)
                }

                MediatorResult.Success(endOfPaginationReached = videos.size < limit)
            },
            onFailure = { throwable ->
                Log.i("VIDEO_PAGING", "response error: ${throwable.message}")

                val hasData = when (loadType) {
                    LoadType.REFRESH -> dao.getCount() > 0
                    else -> true
                }
                if (hasData && throwable is UnknownHostException) {
                    MediatorResult.Success(endOfPaginationReached = false)
                } else if (hasData){
                    MediatorResult.Success(endOfPaginationReached = false)
                } else {
                    MediatorResult.Error(throwable)
                }
            }
        )
    }
}

private inline fun <reified T> executeRequest(call: () -> Response<T>): Result<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body is null"))
        } else {
            Result.failure(HttpException(response))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
