package com.example.prueba_tecnica.presenter.character

import com.example.prueba_tecnica.base.view.IBaseFragment

interface IListCharacterPresenter : IBaseFragment {

    fun getAllListByPage(page: String?)
    fun callServiceByFilter(name: String, status: String, gender: String)
}
