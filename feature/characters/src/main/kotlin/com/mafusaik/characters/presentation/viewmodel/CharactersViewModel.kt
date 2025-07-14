@file:OptIn(FlowPreview::class)

package com.mafusaik.characters.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mafusaik.characters.domain.model.CharacterFilterType
import com.mafusaik.characters.domain.model.CharacterFilters
import com.mafusaik.characters.domain.model.CharacterGender
import com.mafusaik.characters.domain.model.CharacterSearchState
import com.mafusaik.characters.domain.model.CharacterStatus
import com.mafusaik.characters.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

private var searchJob: Job? = null

    private val _state = MutableStateFlow(CharacterSearchState())
    val state: StateFlow<CharacterSearchState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        search()
        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .distinctUntilChanged()
                .collect { query ->
                    Log.i("QUERY_CHANGE", "viewmodel query $query")
                    _state.update { it.copy(query = query) }
                    search()
                }
        }
    }

    fun onQueryChange(query: String) {
        Log.i("QUERY_CHANGE", "onQueryChange $query")
       // _searchQuery.update { query }
        _searchQuery.value = query
        //_state.update { it.copy(query = query) }
       // search()
    }

    fun onFilterChange(type: CharacterFilterType, value: String) {
        Log.i("QUERY_CHANGE", "onFilterChange")
        _state.update {
            it.copy(
                filters = when (type) {
                    CharacterFilterType.STATUS -> {
                        val status = try {
                            if (value.isNotEmpty()) CharacterStatus.valueOf(value.uppercase()) else null
                        } catch (e: IllegalArgumentException) {
                            null
                        }
                        _state.value.filters.copy(status = status)
                    }

                    CharacterFilterType.GENDER -> {
                        val gender = try {
                            if (value.isNotEmpty()) CharacterGender.valueOf(value.uppercase()) else null
                        } catch (e: IllegalArgumentException) {
                            null
                        }
                        _state.value.filters.copy(gender = gender)
                    }

                    CharacterFilterType.SPECIES -> _state.value.filters.copy(species = value.ifEmpty { null })
                    CharacterFilterType.TYPE -> _state.value.filters.copy(type = value.ifEmpty { null })
                }
            )
        }
        search()
    }

    fun resetFilters() {
        Log.i("QUERY_CHANGE", "resetFilters")
        _state.update{it.copy(filters = CharacterFilters())}
        search()
    }

    fun search() {
        Log.i("QUERY_CHANGE", "search")
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            //_state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val result = repository.getCharacters(
                    name = _searchQuery.value,
                    status = _state.value.filters.status,
                    gender = _state.value.filters.gender,
                    species = _state.value.filters.species,
                    type = _state.value.filters.type
                )
                _state.update { it.copy( characters = result,
//                    isLoading = false
                ) }
            } catch (e: Exception) {
//                _state.update { it.copy(isLoading = false)}
//                _state.value = _state.value.copy(
//                    error = e.message ?: "Unknown error",
//                    isLoading = false
//                )
            }
        }
    }
}

