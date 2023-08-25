package com.example.prueba_tecnica.presenter.character

import com.example.prueba_tecnica.base.presenter.BasePresenter
import com.example.prueba_tecnica.model.character.CharacterModel
import com.example.prueba_tecnica.model.episode.EpisodeModel
import com.example.prueba_tecnica.view.fragment.character.IDetailCharacterFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * class to show character detail
 */
class DetailCharacterPresenter : BasePresenter<IDetailCharacterFragment>(),
        IDetailCharacterPresenter {

    /**
     * call to refresh character data by id
     */
    override fun callServiceById(id: String?) {
        id?.apply {
            view?.showLoading()
            val call = apiInterface.getOneCharacter(this)
            call.enqueue(object : Callback<CharacterModel> {
                override fun onResponse(
                        call: Call<CharacterModel>,
                        response: Response<CharacterModel>
                ) {
                    response.body()
                        ?.let {
                            view?.setCharacter(it)
                            filterFirstAndLastEpisode(it)
                        } ?: view?.showError(goBack = true)
                }

                override fun onFailure(
                        call: Call<CharacterModel>,
                        t: Throwable
                ) {
                    view?.showError(goBack = true)
                }
            })
        } ?: view?.showError(goBack = true)
    }

    /**
     * In this method, the last episode and the first one are filtered
     * to obtain the ids, to later make the data calls
     */
    private fun filterFirstAndLastEpisode(character: CharacterModel) {
        val firstEpisodeId = character.episode.first()
            .split("/")
            .last()
        val lastEpisodeId = character.episode.last()
            .split("/")
            .last()
        getEpisodeLastAndFirst(firstEpisodeId, false)
        getEpisodeLastAndFirst(lastEpisodeId, true)
    }

    /**
     * call to extract the specific data of the first and last episodes
     */
    private fun getEpisodeLastAndFirst(
            idEpisode: String,
            isLastEpisode: Boolean
    ) {
        val call = apiInterface.getEpisodeById(idEpisode)
        call.enqueue(object : Callback<EpisodeModel> {
            override fun onResponse(
                    call: Call<EpisodeModel>,
                    response: Response<EpisodeModel>
            ) {
                response.body()
                    ?.let { episode ->
                        if (isLastEpisode) {
                            view?.setLastEpisode(episode)
                            view?.hiddenLoading()
                        } else view?.setFirstEpisode(episode)
                    } ?: view?.hiddenLoading()
            }

            override fun onFailure(
                    call: Call<EpisodeModel>,
                    t: Throwable
            ) {
                view?.hiddenLoading()
            }
        })
    }
}