package com.example.prueba_tecnica.presenter.character

import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.base.presenter.BasePresenter
import com.example.prueba_tecnica.model.GeneralGetModel
import com.example.prueba_tecnica.model.character.CharacterModel
import com.example.prueba_tecnica.view.fragment.character.IListCharacterView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * class to display the character list
 */
class ListCharacterPresenter : BasePresenter<IListCharacterView>(), IListCharacterPresenter {

    companion object {
        private const val KEY_ALL = "Todos"
    }

    /**
     * Call to bring all the characters or next pages
     */
    override fun getAllListByPage(page: String?) {
        view?.showLoading()
        val call = page?.let {
            apiInterface.getAllListCharacterByPage(page)
        } ?: apiInterface.getAllListCharacter()
        call.enqueue(object : Callback<GeneralGetModel<CharacterModel>> {
            override fun onResponse(
                    call: Call<GeneralGetModel<CharacterModel>>,
                    response: Response<GeneralGetModel<CharacterModel>>
            ) {
                response.body()
                    ?.let {
                        view?.setListCharacter(it.results)
                        view?.setInfoPage(it.info)
                        view?.hiddenLoading()
                    } ?: view?.showError { getAllListByPage(page) }
            }

            override fun onFailure(
                    call: Call<GeneralGetModel<CharacterModel>>,
                    t: Throwable
            ) {
                view?.showError { getAllListByPage(page) }
            }
        })
    }

    /**
     * Call to fetch filter by name, status or gender
     */
    override fun callServiceByFilter(
            name: String,
            status: String,
            gender: String
    ) {
        view?.showLoading()
        val statusString = if (status == KEY_ALL) "" else status.lowercase()
        val genderString = if (gender == KEY_ALL) "" else gender.lowercase()
        val call = apiInterface.getFilterCharacter(name, statusString, genderString)
        call.enqueue(object : Callback<GeneralGetModel<CharacterModel>> {
            override fun onResponse(
                    call: Call<GeneralGetModel<CharacterModel>>,
                    response: Response<GeneralGetModel<CharacterModel>>
            ) {
                response.body()
                    ?.let {
                        view?.setListCharacter(it.results)
                        view?.setInfoPage(it.info)
                        view?.hiddenLoading()
                    } ?: view?.showError(
                    messageCustom = R.string.dialog_error_filter_empty_title, goBack = true
                ) { getAllListByPage(null) }
            }

            override fun onFailure(
                    call: Call<GeneralGetModel<CharacterModel>>,
                    t: Throwable
            ) {
                view?.showError(
                    messageCustom = R.string.dialog_error_filter_empty_title, goBack = true
                ) { getAllListByPage(null) }
            }
        })
    }
}