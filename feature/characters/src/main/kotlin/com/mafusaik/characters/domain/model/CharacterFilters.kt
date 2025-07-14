package com.mafusaik.characters.domain.model

data class CharacterFilters(
    val status: CharacterStatus? = null,
    val gender: CharacterGender? = null,
    val species: String? = null,
    val type: String? = null
) {
    fun hasActiveFilters(): Boolean =
        status != null || gender != null || species != null || type != null

    fun getActiveFilters(): Map<CharacterFilterType, String> = buildMap {
        status?.let { put(CharacterFilterType.STATUS, it.value) }
        gender?.let { put(CharacterFilterType.GENDER, it.value) }
        species?.let { put(CharacterFilterType.SPECIES, it) }
        type?.let { put(CharacterFilterType.TYPE, it) }
    }
}