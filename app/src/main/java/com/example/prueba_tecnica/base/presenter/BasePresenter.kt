package com.example.prueba_tecnica.base.presenter

import com.example.prueba_tecnica.retrofit.APIClient
import com.example.prueba_tecnica.retrofit.APIInterface

abstract class BasePresenter<T> : IBasePresenter<T> {

    open var view: T? = null
    open lateinit var apiInterface: APIInterface

    override fun attactView(view: T) {
        this.view = view
        apiInterface = APIClient.getClient()
            .create(APIInterface::class.java)
    }
}