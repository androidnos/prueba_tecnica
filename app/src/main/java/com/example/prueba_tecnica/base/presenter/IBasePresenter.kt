package com.example.prueba_tecnica.base.presenter

import okhttp3.Cache

interface IBasePresenter<T> {
    fun attactView(view: T, cache: Cache) {}
}