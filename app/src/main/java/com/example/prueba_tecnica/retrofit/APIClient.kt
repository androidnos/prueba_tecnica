package com.example.prueba_tecnica.retrofit

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Documentation public api https://dog.ceo/dog-api/
 */
class APIClient {
    companion object {
        private const val API_BASE_URL = "https://rickandmortyapi.com/api/"
        const val SIZE_MB = 10
        const val ONE_MB_IN_KB = 1024

        fun getClient(cache: Cache): Retrofit {
            val client: OkHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .build()
            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}