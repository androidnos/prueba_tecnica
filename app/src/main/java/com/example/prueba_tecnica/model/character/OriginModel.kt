package com.example.prueba_tecnica.model.character

import com.google.gson.annotations.SerializedName

data class OriginModel(
        @SerializedName("name") val name: String? = null,
        @SerializedName("url") val url: String? = null
)
