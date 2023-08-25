package com.example.prueba_tecnica.base.presenter

interface IBasePresenter<T> {
    fun attactView(view: T) {}
}