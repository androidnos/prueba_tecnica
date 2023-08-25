package com.example.prueba_tecnica.retrofit

import com.example.prueba_tecnica.model.GeneralGetModel
import com.example.prueba_tecnica.model.character.CharacterModel
import com.example.prueba_tecnica.model.episode.EpisodeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface APIInterface {
    @GET("character")
    fun getAllListCharacter(): Call<GeneralGetModel<CharacterModel>>

    @GET()
    fun getAllListCharacterByPage(@Url url: String): Call<GeneralGetModel<CharacterModel>>

    @GET("character/{id}")
    fun getOneCharacter(@Path("id") id: String): Call<CharacterModel>

    @GET("character/")
    fun getFilterCharacter(
            @Query("name") name: String,
            @Query("status") status: String,
            @Query("gender") gender: String
    ): Call<GeneralGetModel<CharacterModel>>

    @GET("episode/{id}")
    fun getEpisodeById(
            @Path("id") id: String
    ): Call<EpisodeModel>
}