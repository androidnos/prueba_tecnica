package com.example.prueba_tecnica.base.view

interface IBaseFragment {
    fun showLoading() {}
    fun hiddenLoading() {}
    fun showError(
            messageCustom: Int? = null,
            goBack: Boolean = false,
            runnable: Runnable? = null
    ) {
    }

    fun setToolbar(
            title: String?,
            isHomeButtonEnabled: Boolean = false
    ) {
    }
}