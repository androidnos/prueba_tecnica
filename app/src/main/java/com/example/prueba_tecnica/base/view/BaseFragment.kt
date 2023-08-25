package com.example.prueba_tecnica.base.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.databinding.LoadingCustomBinding
import com.example.prueba_tecnica.retrofit.APIClient
import okhttp3.Cache

abstract class BaseFragment<BasePresenter> : Fragment(), IBaseFragment {

    open var presenter: BasePresenter? = null
    private var alertDialogCustom: AlertDialog? = null
    protected fun getCache(): Cache {
        val cacheSize =
            (APIClient.SIZE_MB * APIClient.ONE_MB_IN_KB * APIClient.ONE_MB_IN_KB).toLong()
        return Cache(requireContext().cacheDir, cacheSize)
    }

    companion object {
        private const val TIMER = 3000L
        private const val RADIO_ROTATION = 360f
        private const val VALUE = 0.5f
    }

    protected fun getActivityInit(): MainActivity = (activity as MainActivity)

    /**
     * method to show generic loading of the app
     */
    @SuppressLint("SetTextI18n")
    override fun showLoading() {
        val bindingCustom = LoadingCustomBinding.inflate(
            LayoutInflater.from(requireContext()), null, false
        )
        val text = getString(R.string.loading_title) + "<br>" + getString(
            R.string.loading_message
        )
        bindingCustom.nameTextView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)

        // here an animation is created for loading
        val rotate = RotateAnimation(
            0f, RADIO_ROTATION, Animation.RELATIVE_TO_SELF, VALUE,
            Animation.RELATIVE_TO_SELF, VALUE
        )
        rotate.duration = TIMER
        rotate.interpolator = LinearInterpolator()
        rotate.repeatCount = Animation.INFINITE
        bindingCustom.iconImageView.startAnimation(rotate)
        val builder = AlertDialog.Builder(requireContext())
            .setView(bindingCustom.root)
        alertDialogCustom = builder.create()
        alertDialogCustom?.show()
    }

    /**
     * method for generic dismiss loading of the app
     */
    override fun hiddenLoading() {
        alertDialogCustom?.dismiss()
    }

    /**
     * method to show an error and perform one action or
     * another with the runnable
     */
    override fun showError(
            messageCustom: Int?,
            goBack: Boolean,
            runnable: Runnable?
    ) {
        hiddenLoading()
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(messageCustom ?: R.string.dialog_error_title)
            .setPositiveButton(
                if (goBack) R.string.go_to_back
                else R.string.acept_dialog_error
            ) { _, _ ->
                runnable?.apply {
                    run()
                } ?: requireActivity().supportFragmentManager.popBackStack()
            }
        builder.create()
        builder.show()
    }

    /**
     * method to configure the toolbar, both the title and the back button
     */
    override fun setToolbar(
            title: String?,
            isHomeButtonEnabled: Boolean
    ) {
        (activity as MainActivity).supportActionBar?.apply {
            this.title = title
            setDisplayHomeAsUpEnabled(isHomeButtonEnabled)
        }
    }

    protected fun configureTextHtmlTextView(
            idString: Int,
            text: String?,
            textView: TextView
    ) {
        text?.let {
            val string = getString(idString) + "<br>" + it
            textView.visibility = View.VISIBLE
            textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY)
        } ?: kotlin.run { textView.visibility = View.GONE }
    }
}