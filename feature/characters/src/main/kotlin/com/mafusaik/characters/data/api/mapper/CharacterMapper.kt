package com.mafusaik.characters.data.api.mapper

import com.mafusaik.characters.data.api.response.CharacterDto
import com.mafusaik.characters.domain.model.Character
import model.CharacterEntity


fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        image = image,
        episodeList = episode,
        status = status,
        type = type,
        gender = gender,
        species = species,
        origin = origin.name
    )
}

fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        image = image,
        episodeList = episode,
        status = status,
        type = type,
        gender = gender,
        species = species,
        origin = origin.name
    )
}

fun CharacterEntity.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        image = image,
        episodeList = episodeList,
        status = status,
        type = type,
        gender = gender,
        species = species,
        origin = origin
    )
}
