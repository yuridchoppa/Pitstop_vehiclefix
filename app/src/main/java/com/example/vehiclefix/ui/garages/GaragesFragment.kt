package com.example.vehiclefix.ui.garages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehiclefix.R
import com.example.vehiclefix.data.models.GarageSpecialty
import com.example.vehiclefix.data.repository.GarageRepository
import com.example.vehiclefix.databinding.FragmentGaragesBinding
import com.example.vehiclefix.ui.adapters.GarageAdapter

class GaragesFragment : Fragment() {

    private var _binding: FragmentGaragesBinding? = null
    private val binding get() = _binding!!

    private lateinit var garageAdapter: GarageAdapter
    private var selectedSpecialty = GarageSpecialty.ALL

    private val searchDebounceHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGaragesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpecialtyChips()
        setupSearch()
        setupEmergencyTowButton()

        refreshGarages()
    }

    private fun setupRecyclerView() {
        garageAdapter = GarageAdapter(emptyList())
        binding.rvGarages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGarages.adapter = garageAdapter
    }

    private fun setupSpecialtyChips() {
        binding.chipGroupSpecialty.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedSpecialty = when {
                checkedIds.contains(R.id.chipSpecThreeWheeler) -> GarageSpecialty.THREE_WHEELER
                checkedIds.contains(R.id.chipSpecTwoWheeler) -> GarageSpecialty.TWO_WHEELER
                checkedIds.contains(R.id.chipSpecCar) -> GarageSpecialty.CAR
                checkedIds.contains(R.id.chipSpecTowing) -> GarageSpecialty.TOWING_RESCUE
                checkedIds.contains(R.id.chipSpecEv) -> GarageSpecialty.EV_BATTERY
                checkedIds.contains(R.id.chipSpecTire) -> GarageSpecialty.TIRE_PUNCTURE
                else -> GarageSpecialty.ALL
            }
            refreshGarages()
        }
    }

    private fun setupSearch() {
        binding.etSearchGarage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchRunnable?.let { searchDebounceHandler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    refreshGarages()
                }
                searchDebounceHandler.postDelayed(searchRunnable!!, 150)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupEmergencyTowButton() {
        binding.btnEmergencyTow.setOnClickListener {
            val nearestTow = GarageRepository.getNearestEmergencyRescue()
            if (nearestTow != null) {
                try {
                    val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse(nearestTow.getDialUri()))
                    startActivity(dialIntent)
                } catch (_: Exception) {
                    Toast.makeText(requireContext(), "Calling 24/7 Tow: ${nearestTow.phone}", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), "Calling Highway Helpline 1033", Toast.LENGTH_SHORT).show()
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1033"))
                startActivity(dialIntent)
            }
        }
    }

    private fun refreshGarages() {
        val query = binding.etSearchGarage.text?.toString()?.trim().orEmpty()
        val list = if (query.isNotEmpty()) {
            GarageRepository.searchGarages(query)
        } else {
            GarageRepository.getGaragesBySpecialty(selectedSpecialty)
        }

        garageAdapter.updateGarages(list)
        binding.tvGarageCount.text = "${list.size} Garages & Rescue Centers Near You:"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchRunnable?.let { searchDebounceHandler.removeCallbacks(it) }
        _binding = null
    }
}
