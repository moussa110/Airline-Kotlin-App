package com.example.airlinesapp.ui.airlineslist

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.airlinesapp.R
import com.example.airlinesapp.data.pojo.AddAirlineRequest
import com.example.airlinesapp.data.pojo.Airline
import com.example.airlinesapp.databinding.FragmentAirlinesListBinding
import com.example.airlinesapp.ui.MainActivity
import com.example.airlinesapp.ui.addairline.AddAirlineBottomDialogFragment
import com.example.airlinesapp.util.Resource
import com.example.airlinesapp.util.SearchCase
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AirlinesListFragment:Fragment(R.layout.fragment_airlines_list) {

    private val viewModel: AirlinesViewModel by viewModels()
    private val adapter = AirlinesListAdapter()
    private lateinit var binding: FragmentAirlinesListBinding
    private val addAirlineDialogFragment = AddAirlineBottomDialogFragment.newInstance()
    private var searchQuery:String? = null
    private lateinit var searchView: SearchView
    private lateinit var searchEditText: EditText
    private var searchCase = SearchCase.NAME
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAirlinesListBinding.bind(view)
        binding.apply {
            (requireActivity() as MainActivity).setSupportActionBar(topAppBarLayout.findViewById(R.id.topAppBar))
            airlinesRv.adapter = adapter
            addBtn.setOnClickListener {
                addAirlineDialogFragment.show(requireActivity().supportFragmentManager,addAirlineDialogFragment.tag)
            }
        }

        recyclerViewClick()
        addNewAirline()
        subscribeToLiveData()

    }

    private fun addNewAirline() {
        addAirlineDialogFragment.setOnButtonClickListener = object : AddAirlineBottomDialogFragment.ButtonClickListener{
            override fun onClick(addAirline: AddAirlineRequest) {
                flag = true
                viewModel.addNewAirline(addAirline)

            }
        }
    }

    private fun recyclerViewClick() {
        adapter.onAirlineClickListener = object :AirlinesListAdapter.SetOnAirlineClickListener{
            override fun onClick(airline: Airline) {
                val action = AirlinesListFragmentDirections.actionAirlinesListFragmentToAirlineDetailsFragment(airline)
                findNavController().navigate(action)
            }
        }
    }

    private fun subscribeToLiveData() {
        binding.apply {
            viewModel.airlinesLiveData.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result.data)
                progress.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage

            }

            viewModel.addAirlineResultLiveData.observe(viewLifecycleOwner){result ->
                var message:String=""
                result?.let {
                    message = "airline added successfully"
                    viewModel.getAllAirlines()
                } ?: {
                    message = "an error occurred"}.toString()
                    Snackbar.make(requireView(),message,Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar,menu)
        val search = menu.findItem(R.id.nav_search)
        searchView = search.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_by_name)
            searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText.setTextColor(resources.getColor(R.color.white))

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery = query
                search(aCase = searchCase)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = newText
                search(aCase = searchCase)
                return true
            }

        })
        searchView.setOnCloseListener {
                adapter.submitList(viewModel.airlinesLiveData.value!!.data)
            false
        }
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_search_by_country -> {
                search(SearchCase.COUNTRY)
                searchEditText.inputType = InputType.TYPE_CLASS_TEXT
                searchView.queryHint = resources.getString(R.string.search_by_country)
                searchEditText.setText("")
            }
            R.id.nav_search_by_name -> {
                search(SearchCase.NAME)
                searchEditText.inputType = InputType.TYPE_CLASS_TEXT
                searchView.queryHint = resources.getString(R.string.search_by_name)
                searchEditText.setText("")
            }
            R.id.nav_search_by_id -> {
                search(SearchCase.ID)
                searchEditText.inputType = InputType.TYPE_CLASS_NUMBER
                searchView.queryHint = resources.getString(R.string.search_by_id)
                searchEditText.setText("")
            }
        }

        return true
    }

    private fun search(aCase : SearchCase) {
        searchCase = aCase
        if (searchQuery.isNullOrEmpty() || searchQuery.isNullOrBlank()) {
            //open search view
            searchView.isIconified = false

        } else {
            adapter.submitList(listOf())
            viewModel.searchByQuery(aCase,searchQuery!!).observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
            searchQuery = null
        }
    }
}