package com.example.vehiclefix.ui.mygarage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.vehiclefix.R
import com.example.vehiclefix.data.models.VehicleProfile
import com.example.vehiclefix.data.models.VehicleType
import com.example.vehiclefix.data.repository.AuthRepository
import com.example.vehiclefix.data.repository.UserVehicleRepository
import com.example.vehiclefix.databinding.FragmentMyGarageBinding
import com.example.vehiclefix.ui.MainActivity
import com.example.vehiclefix.ui.auth.AuthActivity
import com.example.vehiclefix.ui.health.VehicleHealthMonitorActivity
import com.example.vehiclefix.util.ThemeManager
import androidx.appcompat.app.AppCompatDelegate

class MyGarageFragment : Fragment() {

    private var _binding: FragmentMyGarageBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRepo: UserVehicleRepository
    private lateinit var authRepo: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyGarageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRepo = UserVehicleRepository(requireContext())
        authRepo = AuthRepository(requireContext())

        updateUi()
        setupListeners()
        setupThemeToggle()
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    private fun setupThemeToggle() {
        val savedMode = ThemeManager.getSavedThemeMode(requireContext())

        // Clear listeners while setting initial checked button to avoid spurious triggers
        binding.toggleThemeMode.clearOnButtonCheckedListeners()
        when (savedMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.toggleThemeMode.check(R.id.btnThemeLight)
            AppCompatDelegate.MODE_NIGHT_YES -> binding.toggleThemeMode.check(R.id.btnThemeDark)
            else -> binding.toggleThemeMode.check(R.id.btnThemeAuto)
        }

        binding.toggleThemeMode.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val newMode = when (checkedId) {
                    R.id.btnThemeLight -> AppCompatDelegate.MODE_NIGHT_NO
                    R.id.btnThemeDark -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                ThemeManager.setNightMode(requireContext(), newMode, activity)
            }
        }
    }

    private fun setupListeners() {
        binding.btnOpenVehicleLibrary.setOnClickListener {
            startActivity(Intent(requireContext(), com.example.vehiclefix.ui.library.VehicleLibraryActivity::class.java))
        }

        binding.cardVehicleLibrary.setOnClickListener {
            startActivity(Intent(requireContext(), com.example.vehiclefix.ui.library.VehicleLibraryActivity::class.java))
        }

        binding.btnOpenSettings.setOnClickListener {
            startActivity(Intent(requireContext(), com.example.vehiclefix.ui.settings.SettingsActivity::class.java))
        }

        binding.btnAuthAction.setOnClickListener {
            if (authRepo.isLoggedIn()) {
                showLogoutConfirmation()
            } else {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
        }

        binding.btnOpenHealthMonitor.setOnClickListener {
            startActivity(Intent(requireContext(), VehicleHealthMonitorActivity::class.java))
        }

        binding.btnSwitchToAuto.setOnClickListener {
            val vehicles = userRepo.getVehicles()
            val auto = vehicles.find { it.vehicleType == VehicleType.THREE_WHEELER }
            if (auto != null) {
                userRepo.setDefaultVehicle(auto.id)
                Toast.makeText(requireContext(), "Switched to ${auto.make} ${auto.model}", Toast.LENGTH_SHORT).show()
                updateUi()
            }
        }

        binding.btnSwitchToBike.setOnClickListener {
            val vehicles = userRepo.getVehicles()
            val bike = vehicles.find { it.vehicleType == VehicleType.TWO_WHEELER }
            if (bike != null) {
                userRepo.setDefaultVehicle(bike.id)
                Toast.makeText(requireContext(), "Switched to ${bike.make} ${bike.model}", Toast.LENGTH_SHORT).show()
                updateUi()
            }
        }

        binding.btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }

        binding.btnDiagnoseActiveVehicle.setOnClickListener {
            (activity as? MainActivity)?.navigateToTab(R.id.nav_diagnose)
        }
    }

    private var activeBottomSheet: com.google.android.material.bottomsheet.BottomSheetDialog? = null
    private var activeDialog: AlertDialog? = null

    private fun showEditProfileDialog() {
        val context = context ?: return
        activeBottomSheet?.dismiss()
        val dialog = com.google.android.material.bottomsheet.BottomSheetDialog(context)
        activeBottomSheet = dialog
        val view = layoutInflater.inflate(R.layout.dialog_edit_profile, null)
        dialog.setContentView(view)

        val user = authRepo.getCurrentUser() ?: return
        val etName = view.findViewById<android.widget.EditText>(R.id.etEditName)
        val etBio = view.findViewById<android.widget.EditText>(R.id.etEditBio)
        val etLicense = view.findViewById<android.widget.EditText>(R.id.etEditLicense)
        val etPhone = view.findViewById<android.widget.EditText>(R.id.etEditPhone)
        val btnClose = view.findViewById<android.widget.ImageButton>(R.id.btnCloseEditProfile)
        val btnSave = view.findViewById<android.widget.Button>(R.id.btnSaveProfile)

        etName.setText(user.name)
        etBio.setText(user.bio)
        etLicense.setText(user.licenseNumber)
        etPhone.setText(user.phone)

        btnClose.setOnClickListener { dialog.dismiss() }

        btnSave.setOnClickListener {
            val newName = etName.text.toString().trim().ifEmpty { user.name }
            val newBio = etBio.text.toString().trim().ifEmpty { user.bio }
            val newLicense = etLicense.text.toString().trim().ifEmpty { user.licenseNumber }
            val newPhone = etPhone.text.toString().trim().ifEmpty { user.phone }

            val updatedUser = user.copy(
                name = newName,
                bio = newBio,
                licenseNumber = newLicense,
                phone = newPhone
            )
            authRepo.saveProfile(updatedUser)
            dialog.dismiss()
            updateUi()
            Toast.makeText(requireContext(), "✅ Profile bio updated successfully!", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }

    private fun showLogoutConfirmation() {
        val context = context ?: return
        activeDialog?.dismiss()
        activeDialog = AlertDialog.Builder(context)
            .setTitle("Log Out of Pitstop")
            .setMessage("Are you sure you want to log out of your account?")
            .setPositiveButton("Log Out") { _, _ ->
                authRepo.logout()
                Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
                updateUi()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddVehicleDialog() {
        val context = context ?: return
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_vehicle, null)
        val rgType = dialogView.findViewById<RadioGroup>(R.id.rgVehicleType)
        val etMakeModel = dialogView.findViewById<EditText>(R.id.etMakeModel)
        val etPlate = dialogView.findViewById<EditText>(R.id.etPlate)
        val etFuel = dialogView.findViewById<EditText>(R.id.etFuel)

        activeDialog?.dismiss()
        activeDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("Save Vehicle") { _, _ ->
                val makeModelStr = etMakeModel.text.toString().trim()
                val plateStr = etPlate.text.toString().trim()
                val fuelStr = etFuel.text.toString().trim()

                val chosenType = when (rgType.checkedRadioButtonId) {
                    R.id.rbTypeCar -> VehicleType.CAR
                    R.id.rbTypeBike -> VehicleType.TWO_WHEELER
                    else -> VehicleType.THREE_WHEELER
                }

                val finalMake = if (makeModelStr.isNotEmpty()) makeModelStr.split(" ").firstOrNull() ?: "Custom" else "Custom"
                val finalModel = if (makeModelStr.isNotEmpty()) makeModelStr else "Vehicle"
                val finalPlate = if (plateStr.isNotEmpty()) plateStr else "DL-01-XX-0000"
                val finalFuel = if (fuelStr.isNotEmpty()) fuelStr else "Petrol / Electric"

                val newVehicle = VehicleProfile(
                    id = "v_" + System.currentTimeMillis(),
                    name = "$finalMake $finalModel",
                    vehicleType = chosenType,
                    make = finalMake,
                    model = finalModel,
                    year = 2023,
                    fuelType = finalFuel,
                    registrationNumber = finalPlate,
                    odometerKm = 21000,
                    isDefault = true
                )

                userRepo.addVehicle(newVehicle)
                userRepo.setDefaultVehicle(newVehicle.id)
                Toast.makeText(requireContext(), "Saved ${chosenType.emoji} $finalMake $finalModel to My Garage!", Toast.LENGTH_SHORT).show()
                updateUi()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateUi() {
        if (!isAdded) return
        // User profile status
        val user = authRepo.getCurrentUser()
        if (user != null) {
            binding.tvUserName.text = user.name
            binding.tvUserEmail.text = user.email
            binding.tvUserBio.text = user.bio
            binding.tvUserMembershipBadge.text = user.membershipTier
            binding.tvUserLicense.text = "🪪 ${user.licenseNumber} (Verified ✓)"
            binding.tvUserPhone.text = "📞 ${user.phone}"
            binding.tvStatVehiclesCount.text = userRepo.getVehicles().size.toString()
            binding.tvStatServicesCount.text = user.totalServicesBooked.toString()
            binding.tvStatFleetHealth.text = "96%"

            binding.btnAuthAction.text = if (authRepo.isLoggedIn()) "Log Out" else "Sign In"
        }

        // Active vehicle details
        val active = userRepo.getActiveVehicle()
        binding.tvActiveVehicleEmoji.text = active.vehicleType.emoji
        binding.tvActiveVehicleName.text = "${active.make} ${active.model} (${active.year})"
        binding.tvActiveVehicleNumber.text = "${active.registrationNumber} • ${active.fuelType}"
        binding.tvActiveOdometer.text = String.format("%,d km", active.odometerKm)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activeBottomSheet?.dismiss()
        activeBottomSheet = null
        activeDialog?.dismiss()
        activeDialog = null
        _binding = null
    }
}
