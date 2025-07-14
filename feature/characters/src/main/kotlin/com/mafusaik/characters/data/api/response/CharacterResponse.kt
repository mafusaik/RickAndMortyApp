package com.mafusaik.characters.data.api.response

import com.google.gson.annotations.SerializedName


data class CharacterResponse(
    @SerializedName("info") val info: PageInfo,
    @SerializedName("results") val results: List<CharacterDto>
)

data class PageInfo(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)

data class CharacterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("status") val status: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("species") val species: String,
    @SerializedName("episode") val episode: List<String>,
    @SerializedName("origin") val origin: Origin,

)

data class Origin(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
