package com.example.prueba_tecnica.model

import com.google.gson.annotations.SerializedName

data class InfoModel(
        @SerializedName("count") val count: Int? = null,
        @SerializedName("pages") val pages: Int? = null,
        @SerializedName("next") val next: String? = null,
        @SerializedName("prev") val prev: String? = null
)
