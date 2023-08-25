package com.example.prueba_tecnica.presenter.character

import com.example.prueba_tecnica.base.BaseTestPresenterTest
import com.example.prueba_tecnica.model.character.CharacterModel
import com.example.prueba_tecnica.model.episode.EpisodeModel
import com.example.prueba_tecnica.retrofit.APIInterface
import com.example.prueba_tecnica.view.fragment.character.IDetailCharacterFragment
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCharacterPresenterTest : BaseTestPresenterTest() {

    private lateinit var testerPresenter: DetailCharacterPresenter
    private val view: IDetailCharacterFragment = mockkRelaxed()

    @Before
    fun init() {
        testerPresenter = spyk(recordPrivateCalls = true)
        testerPresenter.attactView(view)
        testerPresenter.apiInterface = apiInterface
    }

    private fun callServiceById(responseMockk: Response<CharacterModel>?) {
        // Given
        val callMock = slot<Callback<CharacterModel>>()
        val callBack = mockk<Call<CharacterModel>>(relaxed = true)

        // When
        every { apiInterface.getOneCharacter("123") } returns callBack
        every {
            callBack.enqueue(capture(callMock))
        } answers {
            responseMockk?.let {
                callMock.captured.onResponse(mockkRelaxed(), responseMockk)
            } ?: run {
                callMock.captured.onFailure(mockkRelaxed(), throwable)
            }

        }
        testerPresenter.callServiceById("123")
    }

    private fun getEpisodeLastAndFirst(responseMockk: Response<EpisodeModel>?) {
        // Given
        val callMock = slot<Callback<EpisodeModel>>()
        val callBack = mockk<Call<EpisodeModel>>(relaxed = true)

        // When
        every { apiInterface.getEpisodeById("123") } returns callBack
        every {
            callBack.enqueue(capture(callMock))
        } answers {
            responseMockk?.let {
                callMock.captured.onResponse(mockkRelaxed(), responseMockk)
            } ?: run {
                callMock.captured.onFailure(mockkRelaxed(), throwable)
            }

        }
    }

    @Test
    fun `Test callServiceById onFailure view not null id null`() {
        // When
        every { view.showError(goBack = true) } returns Unit
        testerPresenter.callServiceById(null)

        // Then
        verify(exactly = 1) {
            view.showError(goBack = true, runnable = null)
        }
    }

    @Test
    fun `Test callServiceById onFailure view null id null`() {
        // When
        every { testerPresenter.view } returns null
        testerPresenter.callServiceById(null)

        // Then
        verify(exactly = 0) {
            view.showError(goBack = true, runnable = null)
        }
    }

    @Test
    fun `Test callServiceById onFailure view not null`() {
        // When
        every { view.showError(goBack = true) } returns Unit
        callServiceById(null)

        // Then
        verify(exactly = 1) {
            view.showError(goBack = true, runnable = null)
        }
    }

    @Test
    fun `Test callServiceById onFailure view null`() {
        // When
        every { testerPresenter.view } returns null
        callServiceById(null)

        // Then
        verify(exactly = 0) {
            view.showError(goBack = true, runnable = null)
        }
    }

    @Test
    fun `Test callServiceById onResponse body null view null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<CharacterModel>>()

        // When
        every { testerPresenter.view } returns null
        every { responseMockk.body() } returns null
        callServiceById(responseMockk)

        // Then
        verify(exactly = 0) {
            view.showError(goBack = true, runnable = null)
        }
    }

    @Test
    fun `Test callServiceById onResponse body null view not null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<CharacterModel>>()

        // When
        every { view.showError(goBack = true) } returns Unit
        every { responseMockk.body() } returns null
        callServiceById(responseMockk)

        // Then
        verify(exactly = 1) {
            view.showError(goBack = true, runnable = null)
        }
    }

    @Test
    fun `Test callServiceById onResponse body not null view not null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<CharacterModel>>()
        val characterModelMockk = mockkRelaxed<CharacterModel>()

        // When
        every {
            testerPresenter["filterFirstAndLastEpisode"](
                characterModelMockk
            )
        } returns Unit
        every { view.showError(goBack = true) } returns Unit
        every { responseMockk.body() } returns characterModelMockk
        callServiceById(responseMockk)

        // Then
        verify(exactly = 1) {
            view.setCharacter(characterModelMockk)
        }
        verify(exactly = 1) {
            testerPresenter["filterFirstAndLastEpisode"](
                characterModelMockk
            )
        }
    }

    @Test
    fun `Test callServiceById onResponse body not null view null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<CharacterModel>>()
        val characterModelMockk = mockkRelaxed<CharacterModel>()

        // When
        every {
            testerPresenter["filterFirstAndLastEpisode"](
                characterModelMockk
            )
        } returns Unit
        every { testerPresenter.view } returns null
        every { responseMockk.body() } returns characterModelMockk
        callServiceById(responseMockk)

        // Then
        verify(exactly = 0) {
            view.setCharacter(characterModelMockk)
        }
        verify(exactly = 1) {
            testerPresenter["filterFirstAndLastEpisode"](
                characterModelMockk
            )
        }
    }

    @Test
    fun `Test getEpisodeLastAndFirst onResponse body null view not null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<EpisodeModel>>()

        // When
        every { view.hiddenLoading() } returns Unit
        every { responseMockk.body() } returns null
        getEpisodeLastAndFirst(responseMockk)
        testerPresenter.accessMethod(
            "getEpisodeLastAndFirst", String::class.java, Boolean::class.java
        )
            .invoke(testerPresenter, "123", false)

        // Then
        verify(exactly = 1) { view.hiddenLoading() }
    }

    @Test
    fun `Test getEpisodeLastAndFirst onResponse body null view null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<EpisodeModel>>()

        // When
        every { testerPresenter.view } returns null
        every { responseMockk.body() } returns null
        getEpisodeLastAndFirst(responseMockk)
        testerPresenter.accessMethod(
            "getEpisodeLastAndFirst", String::class.java, Boolean::class.java
        )
            .invoke(testerPresenter, "123", false)

        // Then
        verify(exactly = 0) { view.hiddenLoading() }
    }

    @Test
    fun `Test getEpisodeLastAndFirst onFailure body null view not null`() {
        // When
        every { view.hiddenLoading() } returns Unit
        getEpisodeLastAndFirst(null)
        testerPresenter.accessMethod(
            "getEpisodeLastAndFirst", String::class.java, Boolean::class.java
        )
            .invoke(testerPresenter, "123", false)

        // Then
        verify(exactly = 1) { view.hiddenLoading() }
    }

    @Test
    fun `Test getEpisodeLastAndFirst onFailure body null view null`() {
        // When
        every { testerPresenter.view } returns null
        getEpisodeLastAndFirst(null)
        testerPresenter.accessMethod(
            "getEpisodeLastAndFirst", String::class.java, Boolean::class.java
        )
            .invoke(testerPresenter, "123", false)

        // Then
        verify(exactly = 0) { view.hiddenLoading() }
    }

    @Test
    fun `Test getEpisodeLastAndFirst onResponse body not null view not null isLastEpisode false`() {
        // Given
        val responseMockk = mockkRelaxed<Response<EpisodeModel>>()
        val episodeModelMockk = mockkRelaxed<EpisodeModel>()

        // When
        every { view.setFirstEpisode(episodeModelMockk) } returns Unit
        every { responseMockk.body() } returns episodeModelMockk
        getEpisodeLastAndFirst(responseMockk)
        testerPresenter.accessMethod(
            "getEpisodeLastAndFirst", String::class.java, Boolean::class.java
        )
            .invoke(testerPresenter, "123", false)

        // Then
        verify(exactly = 1) { view.setFirstEpisode(episodeModelMockk) }
    }

    @Test
    fun `Test getEpisodeLastAndFirst onResponse body not null view null isLastEpisode false`() {
        // Given
        val responseMockk = mockkRelaxed<Response<EpisodeModel>>()
        val episodeModelMockk = mockkRelaxed<EpisodeModel>()

        // When
        every { testerPresenter.view } returns null
        every { responseMockk.body() } returns episodeModelMockk
        getEpisodeLastAndFirst(responseMockk)
        testerPresenter.accessMethod(
            "getEpisodeLastAndFirst", String::class.java, Boolean::class.java
        )
            .invoke(testerPresenter, "123", false)

        // Then
        verify(exactly = 0) { view.setFirstEpisode(episodeModelMockk) }
    }

    @Test
    fun `Test getEpisodeLastAndFirst onResponse body not null view not null isLastEpisode true`() {
        // Given
        val responseMockk = mockkRelaxed<Response<EpisodeModel>>()
        val episodeModelMockk = mockkRelaxed<EpisodeModel>()

        // When
        every { view.setLastEpisode(episodeModelMockk) } returns Unit
        every { view.hiddenLoading() } returns Unit
        every { responseMockk.body() } returns episodeModelMockk
        getEpisodeLastAndFirst(responseMockk)
        testerPresenter.accessMethod(
            "getEpisodeLastAndFirst", String::class.java, Boolean::class.java
        )
            .invoke(testerPresenter, "123", true)

        // Then
        verify(exactly = 1) { view.setLastEpisode(episodeModelMockk) }
        verify(exactly = 1) { view.hiddenLoading() }
    }

    @Test
    fun `Test getEpisodeLastAndFirst onResponse body not null view null isLastEpisode true`() {
        // Given
        val responseMockk = mockkRelaxed<Response<EpisodeModel>>()
        val episodeModelMockk = mockkRelaxed<EpisodeModel>()

        // When
        every { testerPresenter.view } returns null
        every { responseMockk.body() } returns episodeModelMockk
        getEpisodeLastAndFirst(responseMockk)
        testerPresenter.accessMethod(
            "getEpisodeLastAndFirst", String::class.java, Boolean::class.java
        )
            .invoke(testerPresenter, "123", true)

        // Then
        verify(exactly = 0) { view.setLastEpisode(episodeModelMockk) }
        verify(exactly = 0) { view.hiddenLoading() }
    }

    @Test
    fun `Test filterFirstAndLastEpisode`() {
        // Given
        val characterModelMockk = mockkRelaxed<CharacterModel>()

        // When
        every { characterModelMockk.episode } returns arrayListOf(
            "https://rickandmortyapi.com/api/episode/10",
            "https://rickandmortyapi.com/api/episode/102"
        )
        every { testerPresenter["getEpisodeLastAndFirst"]("10", false) } returns Unit
        every { testerPresenter["getEpisodeLastAndFirst"]("102", true) } returns Unit
        testerPresenter.runMethod("filterFirstAndLastEpisode", characterModelMockk)

        // Then
        verify(exactly = 1) { testerPresenter["getEpisodeLastAndFirst"]("10", false) }
        verify(exactly = 1) { testerPresenter["getEpisodeLastAndFirst"]("102", true) }
    }
}