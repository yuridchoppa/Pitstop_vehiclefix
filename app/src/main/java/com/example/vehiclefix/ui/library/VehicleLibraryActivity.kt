package com.example.vehiclefix.ui.library

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehiclefix.R
import com.example.vehiclefix.data.models.VehicleProfile
import com.example.vehiclefix.data.models.VehicleType
import com.example.vehiclefix.data.repository.LibraryVehicle
import com.example.vehiclefix.data.repository.UserVehicleRepository
import com.example.vehiclefix.data.repository.VehicleLibraryRepository
import com.example.vehiclefix.databinding.ActivityVehicleLibraryBinding
import com.example.vehiclefix.ui.adapters.VehicleLibraryAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class VehicleLibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleLibraryBinding
    private lateinit var libraryAdapter: VehicleLibraryAdapter
    private lateinit var userVehicleRepo: UserVehicleRepository

    private var selectedVehicleType = VehicleType.ALL
    private val debounceHandler = Handler(Looper.getMainLooper())
    private var debounceRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userVehicleRepo = UserVehicleRepository(this)

        setupToolbar()
        setupRecyclerView()
        setupChips()
        setupSearch()

        refreshLibrary()
    }

    private fun setupToolbar() {
        binding.toolbarLibrary.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        libraryAdapter = VehicleLibraryAdapter(
            vehicles = emptyList(),
            onAddToGarageClick = { vehicle ->
                showAddConfirmation(vehicle)
            },
            onViewSpecsClick = { vehicle ->
                showVehicleDetailModal(vehicle)
            }
        )
        binding.rvVehicleLibrary.layoutManager = LinearLayoutManager(this)
        binding.rvVehicleLibrary.adapter = libraryAdapter
    }

    private fun setupChips() {
        binding.chipGroupLibCategory.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedVehicleType = when {
                checkedIds.contains(R.id.chipLibCars) -> VehicleType.CAR
                checkedIds.contains(R.id.chipLibBikes) -> VehicleType.TWO_WHEELER
                checkedIds.contains(R.id.chipLibAutos) -> VehicleType.THREE_WHEELER
                else -> VehicleType.ALL
            }
            refreshLibrary()
        }
    }

    private fun setupSearch() {
        binding.etSearchLibrary.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                debounceRunnable?.let { debounceHandler.removeCallbacks(it) }
                debounceRunnable = Runnable {
                    refreshLibrary()
                }
                debounceHandler.postDelayed(debounceRunnable!!, 150)
            }
        })
    }

    private fun refreshLibrary() {
        val query = binding.etSearchLibrary.text?.toString().orEmpty()
        val filtered = VehicleLibraryRepository.searchVehicles(query, selectedVehicleType)

        libraryAdapter.updateVehicles(filtered)
        binding.tvEmptyLibrary.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
    }

    private var specBottomSheet: BottomSheetDialog? = null
    private var addConfirmDialog: AlertDialog? = null

    private fun showAddConfirmation(vehicle: LibraryVehicle) {
        val randomDigits = (1000..9999).random()
        val randomPlate = "DL-0${(1..9).random()}-AB-$randomDigits"

        addConfirmDialog?.dismiss()
        addConfirmDialog = AlertDialog.Builder(this)
            .setTitle("🚘 Add to My Garage")
            .setMessage("Add ${vehicle.emoji} ${vehicle.make} ${vehicle.model} to your saved garage profile?\n\n" +
                    "• Category: ${vehicle.category}\n" +
                    "• Fuel: ${vehicle.fuelType}\n" +
                    "• Auto Plate: $randomPlate")
            .setPositiveButton("Add Vehicle") { _, _ ->
                val newProfile = VehicleProfile(
                    id = "v_" + System.currentTimeMillis(),
                    name = "${vehicle.make} ${vehicle.model}",
                    vehicleType = vehicle.vehicleType,
                    make = vehicle.make,
                    model = vehicle.model,
                    year = 2023,
                    fuelType = vehicle.fuelType,
                    registrationNumber = randomPlate,
                    odometerKm = 12500,
                    isDefault = true
                )
                userVehicleRepo.addVehicle(newProfile)
                userVehicleRepo.setDefaultVehicle(newProfile.id)
                Toast.makeText(this, "✅ ${vehicle.make} ${vehicle.model} added to Garage & set as active!", Toast.LENGTH_LONG).show()
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showVehicleDetailModal(vehicle: LibraryVehicle) {
        specBottomSheet?.dismiss()
        val dialog = BottomSheetDialog(this)
        specBottomSheet = dialog
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_vehicle_specs, null)
        dialog.setContentView(view)

        view.findViewById<TextView>(R.id.tvSpecTitle).text = "${vehicle.emoji} ${vehicle.make} ${vehicle.model}"
        view.findViewById<TextView>(R.id.tvSpecCategory).text = "${vehicle.category} • ${vehicle.fuelType}"

        view.findViewById<TextView>(R.id.tvSpecEngine).text = vehicle.engineDisplacement
        view.findViewById<TextView>(R.id.tvSpecPower).text = vehicle.powerBhp
        view.findViewById<TextView>(R.id.tvSpecOil).text = vehicle.oilGrade
        view.findViewById<TextView>(R.id.tvSpecTire).text = vehicle.tirePressurePsi
        view.findViewById<TextView>(R.id.tvSpecSparkPlug).text = vehicle.sparkPlugType
        view.findViewById<TextView>(R.id.tvSpecInterval).text = vehicle.serviceIntervalKm

        val issuesText = vehicle.commonIssues.joinToString("\n• ", prefix = "• ")
        view.findViewById<TextView>(R.id.tvSpecKnownIssues).text = issuesText

        view.findViewById<Button>(R.id.btnSpecAddToGarage).setOnClickListener {
            dialog.dismiss()
            showAddConfirmation(vehicle)
        }

        view.findViewById<Button>(R.id.btnSpecClose).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        debounceRunnable?.let { debounceHandler.removeCallbacks(it) }
        specBottomSheet?.dismiss()
        specBottomSheet = null
        addConfirmDialog?.dismiss()
        addConfirmDialog = null
    }
}
