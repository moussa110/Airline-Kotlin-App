package com.example.airlinesapp.ui.airlinedetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.airlinesapp.R
import com.example.airlinesapp.databinding.FragmentAirlineDetailsBinding
import com.example.airlinesapp.ui.MainActivity

class AirlineDetailsFragment:Fragment(R.layout.fragment_airline_details) {
    private val args: AirlineDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentAirlineDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAirlineDetailsBinding.bind(view)
        setupAppBar()
        displayData()
        visitWebsite()
    }

    private fun visitWebsite() {
        binding.apply {
            visitBtn.setOnClickListener {
                it.animate().apply {
                    duration = 1000
                    rotationXBy(360f)
                }.withEndAction {
                    openUrl()
                }
            }
        }
    }

    private fun setupAppBar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.topAppBarLayout.findViewById(R.id.topAppBar))
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayData() {
        val item = args.airline
        binding.apply {

            Glide.with(requireContext())
                .load(item.logo)
                .placeholder(R.drawable.vodafone)
                .into(logoIv)



            nameTv.text = item.name
            countryTv.text = item.country
            sloganTv.text = item.slogan
        }
    }

    private fun openUrl() {
        val defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
        defaultBrowser.data = Uri.parse(args.airline.website)
        startActivity(defaultBrowser)
    }
}