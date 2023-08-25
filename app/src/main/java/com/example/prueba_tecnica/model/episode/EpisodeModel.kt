package com.example.prueba_tecnica.model.episode

import com.google.gson.annotations.SerializedName

data class EpisodeModel(
        @SerializedName("id") val id: Int? = null,
        @SerializedName("name") val name: String? = null,
        @SerializedName("air_date") val airDate: String? = null,
        @SerializedName("episode") val episode: String? = null,
        @SerializedName("characters") val characters: ArrayList<String> = arrayListOf(),
        @SerializedName("url") val url: String? = null,
        @SerializedName("created") val created: String? = null
)
