package com.example.prueba_tecnica.view.fragment.character

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.base.view.BaseFragment
import com.example.prueba_tecnica.base.view.MainActivity
import com.example.prueba_tecnica.databinding.ListCharactersFragmentLayoutBinding
import com.example.prueba_tecnica.databinding.SearchCustomViewBinding
import com.example.prueba_tecnica.model.InfoModel
import com.example.prueba_tecnica.model.character.CharacterModel
import com.example.prueba_tecnica.presenter.character.ListCharacterPresenter
import com.example.prueba_tecnica.view.adapter.GeneralAdapter

class ListCharacterFragment : BaseFragment<ListCharacterPresenter>(), IListCharacterView {

    private lateinit var binding: ListCharactersFragmentLayoutBinding
    private lateinit var adapter: GeneralAdapter
    private var selectGenderString = "-"
    private var selectStatusString = "-"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        presenter = ListCharacterPresenter()
        presenter?.attactView(this, getCache())
        binding = ListCharactersFragmentLayoutBinding.inflate(inflater, container, false)
        initComponentView()
        presenter?.getAllListByPage(null)
        return binding.root
    }

    private fun initComponentView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = GeneralAdapter(arrayListOf(), listener())
        binding.recyclerView.adapter = adapter
        binding.searchButton.setOnClickListener {
            openDialogFilter()
        }
    }

    /**
     * update character list for adapter
     */
    override fun setListCharacter(list: List<CharacterModel?>?) {
        list?.let { items ->
            adapter.updateList(items)
        }
    }

    private fun listener() = object : GeneralAdapter.ListenerAdapter {
        override fun click(idItem: String) {
            val fragment = DetailCharacterFragment()
            fragment.arguments = DetailCharacterFragment.getBundle(idItem)
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }
    }

    /**
     * in this method the next or prev buttons are configured
     */
    override fun setInfoPage(info: InfoModel?) {
        val numPage = info?.prev?.let {
            info.next?.split("=")
                ?.last() ?: info.pages
        } ?: "1"
        setToolbar(resources.getString(R.string.all_character_text) + numPage)
        binding.apply {
            info?.prev?.let { pageUrl ->
                prevButton.setOnClickListener {
                    presenter?.getAllListByPage(pageUrl)
                }
                prevButton.visibility = View.VISIBLE
            } ?: kotlin.run { prevButton.visibility = View.GONE }
            info?.next?.let { pageUrl ->
                nextButton.setOnClickListener {
                    presenter?.getAllListByPage(pageUrl)
                }
                nextButton.visibility = View.VISIBLE
            } ?: kotlin.run { nextButton.visibility = View.GONE }
        }
    }

    /**
     * In this method, the clicks of the spinners of the filter or search view are configured
     */
    private fun configSpinnerClick(bindingCustomBinding: SearchCustomViewBinding) {
        bindingCustomBinding.genderSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    selectGenderString = parent?.getItemAtPosition(position)
                        .toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // not used
                }
            }
        bindingCustomBinding.statusSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    selectStatusString = parent?.getItemAtPosition(position)
                        .toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // not used
                }
            }
    }

    /**
     * In this method the view is opened to filter by name, status and gender
     */
    private fun openDialogFilter() {
        val bindingCustomBinding =
            SearchCustomViewBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.status_list,
            android.R.layout.simple_spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                bindingCustomBinding.statusSpinner.adapter = adapter
            }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_list,
            android.R.layout.simple_spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                bindingCustomBinding.genderSpinner.adapter = adapter
            }
        configSpinnerClick(bindingCustomBinding)
        val builder = AlertDialog.Builder(requireContext())
            .setView(bindingCustomBinding.root)
            .setPositiveButton(R.string.search_button_text) { _, _ ->
                presenter?.callServiceByFilter(
                    bindingCustomBinding.nameEditText.text.toString(), selectStatusString,
                    selectGenderString
                )
            }
            .setNegativeButton(R.string.go_to_back) { _, _ ->
                // not used
            }
        builder.create()
        builder.show()
    }
}