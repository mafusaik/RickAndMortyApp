package com.mafusaik.characters.domain.model


data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val status: String,
    val type: String,
    val gender: String,
    val species: String,
    val origin: String,
    val episodeList: List<String>
)
