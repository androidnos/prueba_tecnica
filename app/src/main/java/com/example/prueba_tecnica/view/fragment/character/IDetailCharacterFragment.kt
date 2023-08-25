package com.example.prueba_tecnica.view.fragment.character

import com.example.prueba_tecnica.base.view.IBaseFragment
import com.example.prueba_tecnica.model.character.CharacterModel
import com.example.prueba_tecnica.model.episode.EpisodeModel

interface IDetailCharacterFragment : IBaseFragment {

    fun setCharacter(character: CharacterModel)
    fun setLastEpisode(episode: EpisodeModel)
    fun setFirstEpisode(episode: EpisodeModel)
}