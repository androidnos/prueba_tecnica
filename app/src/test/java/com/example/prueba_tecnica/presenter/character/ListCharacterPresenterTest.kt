package com.example.prueba_tecnica.presenter.character

import com.example.prueba_tecnica.base.BaseTestPresenterTest
import com.example.prueba_tecnica.model.GeneralGetModel
import com.example.prueba_tecnica.model.character.CharacterModel
import com.example.prueba_tecnica.view.fragment.character.IListCharacterView
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

class ListCharacterPresenterTest : BaseTestPresenterTest() {

    private lateinit var testerPresenter: ListCharacterPresenter
    private val view = mockkRelaxed<IListCharacterView>()

    @Before
    fun init() {
        testerPresenter = spyk(recordPrivateCalls = true)
        testerPresenter.attactView(view, mockkRelaxed())
        testerPresenter.apiInterface = apiInterface
    }

    private fun callAllList(
            responseMockk: Response<GeneralGetModel<CharacterModel>>?,
            page: String? = null
    ) {
        // Given
        val callMock = slot<Callback<GeneralGetModel<CharacterModel>>>()
        val callBack = mockk<Call<GeneralGetModel<CharacterModel>>>(relaxed = true)

        // When
        page?.let {
            every { apiInterface.getAllListCharacterByPage(it) } returns callBack
        } ?: run {
            every { apiInterface.getAllListCharacter() } returns callBack
        }

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

    private fun callServiceByFilter(
            responseMockk: Response<GeneralGetModel<CharacterModel>>?,
            name: String,
            status: String,
            gender: String
    ) {
        // Given
        val callMock = slot<Callback<GeneralGetModel<CharacterModel>>>()
        val callBack = mockk<Call<GeneralGetModel<CharacterModel>>>(relaxed = true)

        // When
        every { apiInterface.getFilterCharacter(name, status, gender) } returns callBack
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
    fun `Test callAllList onFailure view not null call by page`() {
        // When
        every { view.showLoading() } returns Unit
        callAllList(null, "page")
        testerPresenter.getAllListByPage("page")

        // Then
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `Test callAllList onFailure view not null`() {
        // When
        every { view.showLoading() } returns Unit
        callAllList(null)
        testerPresenter.getAllListByPage(null)

        // Then
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `Test callAllList onFailure view null`() {
        // When
        every { testerPresenter.view } returns null
        callAllList(null)
        testerPresenter.getAllListByPage(null)

        // Then
        verify(exactly = 0) { view.showLoading() }
    }

    @Test
    fun `Test callAllList onResponse body null view not null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<GeneralGetModel<CharacterModel>>>()

        // When
        every { view.showLoading() } returns Unit
        every { responseMockk.body() } returns null
        callAllList(responseMockk)
        testerPresenter.getAllListByPage(null)

        // Then
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `Test callAllList onResponse body null view null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<GeneralGetModel<CharacterModel>>>()

        // When
        every { responseMockk.body() } returns null
        every { testerPresenter.view } returns null
        callAllList(null)
        testerPresenter.getAllListByPage(null)

        // Then
        verify(exactly = 0) { view.showLoading() }
    }

    @Test
    fun `Test callAllList onResponse body not null view not null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<GeneralGetModel<CharacterModel>>>()
        val generalGetModelMockk = mockkRelaxed<GeneralGetModel<CharacterModel>>()

        // When
        every { view.setListCharacter(generalGetModelMockk.results) } returns Unit
        every { view.setInfoPage(generalGetModelMockk.info) } returns Unit
        every { view.hiddenLoading() } returns Unit
        every { view.showLoading() } returns Unit
        every { responseMockk.body() } returns generalGetModelMockk
        callAllList(responseMockk)
        testerPresenter.getAllListByPage(null)

        // Then
        verify(exactly = 1) { view.showLoading() }
        verify(exactly = 1) { view.setListCharacter(generalGetModelMockk.results) }
        verify(exactly = 1) { view.setInfoPage(generalGetModelMockk.info) }
        verify(exactly = 1) { view.hiddenLoading() }
    }

    @Test
    fun `Test callAllList onResponse body not null view null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<GeneralGetModel<CharacterModel>>>()
        val generalGetModelMockk = mockkRelaxed<GeneralGetModel<CharacterModel>>()

        // When
        every { testerPresenter.view } returns null
        every { responseMockk.body() } returns generalGetModelMockk
        callAllList(responseMockk)
        testerPresenter.getAllListByPage(null)

        // Then
        verify(exactly = 0) { view.showLoading() }
        verify(exactly = 0) { view.setListCharacter(generalGetModelMockk.results) }
        verify(exactly = 0) { view.setInfoPage(generalGetModelMockk.info) }
        verify(exactly = 0) { view.hiddenLoading() }
    }

    @Test
    fun `Test callServiceByFilter onFailure view not null`() {
        // When
        every { view.showLoading() } returns Unit
        callServiceByFilter(null, "", "", "")
        testerPresenter.callServiceByFilter("", "", "")

        // Then
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `Test callServiceByFilter onFailure view null`() {
        // When
        every { testerPresenter.view } returns null
        callServiceByFilter(null, "", "", "")
        testerPresenter.callServiceByFilter("", "", "")

        // Then
        verify(exactly = 0) { view.showLoading() }
    }

    @Test
    fun `Test callServiceByFilter onResponse body null view not null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<GeneralGetModel<CharacterModel>>>()

        // When
        every { view.showLoading() } returns Unit
        every { responseMockk.body() } returns null
        callServiceByFilter(responseMockk, "", "-", "-")
        testerPresenter.callServiceByFilter("", "-", "-")

        // Then
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `Test callServiceByFilter onResponse body null view null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<GeneralGetModel<CharacterModel>>>()

        // When
        every { testerPresenter.view } returns null
        every { responseMockk.body() } returns null
        callServiceByFilter(responseMockk, "", "", "")
        testerPresenter.callServiceByFilter("", "", "")

        // Then
        verify(exactly = 0) { view.showLoading() }
    }

    @Test
    fun `Test callServiceByFilter onResponse body not null view not null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<GeneralGetModel<CharacterModel>>>()
        val generalGetModelMockk = mockkRelaxed<GeneralGetModel<CharacterModel>>()

        // When
        every { responseMockk.body() } returns generalGetModelMockk
        every { view.showLoading() } returns Unit
        every { view.setListCharacter(generalGetModelMockk.results) } returns Unit
        every { view.setInfoPage(generalGetModelMockk.info) } returns Unit
        every { view.hiddenLoading() } returns Unit
        callServiceByFilter(responseMockk, "", "-", "-")
        testerPresenter.callServiceByFilter("", "-", "-")

        // Then
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `Test callServiceByFilter onResponse body not null view null`() {
        // Given
        val responseMockk = mockkRelaxed<Response<GeneralGetModel<CharacterModel>>>()
        val generalGetModelMockk = mockkRelaxed<GeneralGetModel<CharacterModel>>()

        // When
        every { testerPresenter.view } returns null
        every { responseMockk.body() } returns generalGetModelMockk
        callServiceByFilter(responseMockk, "", "", "")
        testerPresenter.callServiceByFilter("", "", "")

        // Then
        verify(exactly = 0) { view.showLoading() }
        verify(exactly = 0) { view.setListCharacter(generalGetModelMockk.results) }
        verify(exactly = 0) { view.setInfoPage(generalGetModelMockk.info) }
        verify(exactly = 0) { view.hiddenLoading() }
    }
}