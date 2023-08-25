package com.example.prueba_tecnica.view.fragment.character

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.base.view.BaseFragment
import com.example.prueba_tecnica.databinding.DetailCharacterFragmentBinding
import com.example.prueba_tecnica.model.character.CharacterModel
import com.example.prueba_tecnica.model.episode.EpisodeModel
import com.example.prueba_tecnica.presenter.character.DetailCharacterPresenter
import com.squareup.picasso.Picasso

class DetailCharacterFragment : BaseFragment<DetailCharacterPresenter>(),
        IDetailCharacterFragment {

    private lateinit var binding: DetailCharacterFragmentBinding

    companion object {
        private const val KEY_DEAD = "dead"
        private const val KEY_ALIVE = "alive"
        private const val KEY_ID = "key_id_character"

        @JvmStatic
        fun getBundle(id: String): Bundle {
            val argument = Bundle()
            argument.putString(KEY_ID, id)
            return argument
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DetailCharacterFragmentBinding.inflate(inflater, container, false)
        presenter = DetailCharacterPresenter()
        presenter?.attactView(this, getCache())
        presenter?.callServiceById(arguments?.getString(KEY_ID))
        return binding.root
    }

    /**
     * In this method all the texts of the detail view are configured
     */
    @SuppressLint("SetTextI18n")
    override fun setCharacter(character: CharacterModel) {
        binding.apply {
            // With the Picasso library we can access the images and it has a small cache added to load the images faster
            Picasso.get()
                .load(character.image)
                .into(iconImageView)
            nameTextView.text = character.name
            stateColorView.setBackgroundColor(getStateColor(character.status))
            configureTextHtmlTextView(R.string.gender_text, character.gender, genderTextView)
            configureTextHtmlTextView(R.string.specie_text, character.species, specieTextView)
            configureTextHtmlTextView(
                R.string.orige_text, character.origin?.name, originTextView
            )
            configureTextHtmlTextView(
                R.string.location_text, character.location?.name, locationTextView
            )
        }
        setToolbar(
            getString(R.string.character_number_text) + character.id.toString(),
            true
        )
    }

    @SuppressLint("SetTextI18n")
    override fun setLastEpisode(episode: EpisodeModel) {
        configureTextHtmlTextView(
            R.string.last_episode_text, episode.name, binding.lastEpisodeTextView
        )
    }

    @SuppressLint("SetTextI18n")
    override fun setFirstEpisode(episode: EpisodeModel) {
        configureTextHtmlTextView(
            R.string.first_episode_text, episode.name, binding.fisrtEpisodeTextView
        )
    }

    /**
     * In this method the state of the character is configured by colors, as marked in the legend
     */
    private fun getStateColor(state: String?): Int {
        return ContextCompat.getColor(
            requireContext(), when (state?.lowercase()) {
                KEY_ALIVE -> android.R.color.holo_green_dark
                KEY_DEAD -> android.R.color.holo_red_dark
                else -> android.R.color.darker_gray
            }
        )
    }
}
