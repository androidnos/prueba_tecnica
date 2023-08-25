package com.example.prueba_tecnica.model

import com.google.gson.annotations.SerializedName

data class GeneralGetModel<T>(
        @SerializedName("info") val info: InfoModel? = null,
        @SerializedName("results") val results: List<T?>? = null
)
