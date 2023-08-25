package com.example.prueba_tecnica.view.fragment.character

import com.example.prueba_tecnica.base.view.IBaseFragment
import com.example.prueba_tecnica.model.InfoModel
import com.example.prueba_tecnica.model.character.CharacterModel

interface IListCharacterView : IBaseFragment {
    fun setListCharacter(list: List<CharacterModel?>?)
    fun setInfoPage(info: InfoModel?)
}