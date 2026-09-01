package com.example.vehiclefix.ui.diagnose

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefix.R
import com.example.vehiclefix.data.models.VehicleIssue
import com.example.vehiclefix.data.models.VehicleType
import com.example.vehiclefix.data.repository.AuthRepository
import com.example.vehiclefix.data.repository.DiagnosticRepository
import com.example.vehiclefix.data.repository.ScanPreset
import com.example.vehiclefix.data.repository.ServiceBooking
import com.example.vehiclefix.data.repository.ServiceBookingRepository
import com.example.vehiclefix.data.repository.ServiceStatus
import com.example.vehiclefix.data.repository.UserVehicleRepository
import com.example.vehiclefix.data.repository.VehicleIssueRepository
import com.example.vehiclefix.databinding.FragmentDiagnoseBinding
import com.example.vehiclefix.ui.MainActivity
import com.example.vehiclefix.ui.adapters.IssueAdapter
import com.example.vehiclefix.ui.adapters.ScanPresetAdapter
import com.example.vehiclefix.ui.health.VehicleHealthMonitorActivity
import com.example.vehiclefix.ui.issues.IssueDetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DiagnoseFragment : Fragment() {

    private var _binding: FragmentDiagnoseBinding? = null
    private val binding get() = _binding!!

    private lateinit var issueAdapter: IssueAdapter
    private var selectedVehicleType = VehicleType.ALL

    private lateinit var authRepo: AuthRepository
    private lateinit var vehicleRepo: UserVehicleRepository
    private lateinit var serviceRepo: ServiceBookingRepository

    private val searchDebounceHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    // Active bottom sheet dialog reference
    private var currentBottomSheet: BottomSheetDialog? = null

    // Camera Capture Launcher
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            currentBottomSheet?.dismiss()
            processCapturedImage("Live Camera Snapshot")
        }
    }

    // Gallery Picker Launcher
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            currentBottomSheet?.dismiss()
            processCapturedImage("Uploaded Image")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiagnoseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authRepo = AuthRepository(requireContext())
        vehicleRepo = UserVehicleRepository(requireContext())
        serviceRepo = ServiceBookingRepository(requireContext())

        setupHeaderAndPill()
        setupTrackerCard()
        setupMotoristQuickActions()
        setupDiagnosticToolCards()
        setupRecyclerView()
        setupViewAllGuides()
        setupQuickSos()

        refreshIssues()
    }

    override fun onResume() {
        super.onResume()
        updateHeaderAndTracker()
    }

    private fun setupHeaderAndPill() {
        updateHeaderAndTracker()

        binding.btnVehiclePill.setOnClickListener {
            showVehicleSwitchModal()
        }

        binding.cardConnectedVehicleStage.setOnClickListener {
            showVehicleSwitchModal()
        }
    }

    private fun updateHeaderAndTracker() {
        val user = authRepo.getCurrentUser()
        if (user != null && authRepo.isLoggedIn()) {
            binding.tvHomeGreeting.text = "Hello, ${user.name}! 👋"
        } else {
            binding.tvHomeGreeting.text = "Welcome to Pitstop ⚡"
        }

        val active = vehicleRepo.getActiveVehicle()
        binding.btnVehiclePill.text = "${active.vehicleType.emoji} ${active.make} ${active.model} [${active.registrationNumber.take(5)}] ▾"

        // Connected Vehicle Hero Stage
        binding.tvStageVehicleEmoji.text = active.vehicleType.emoji
        binding.tvStageVehicleName.text = "${active.make} ${active.model} (${active.year})"
        binding.tvStageVehiclePlate.text = "${active.registrationNumber} • ${active.fuelType}"
        binding.tvStageFuel.text = if (active.fuelType.contains("Electric", true)) "82% (380 km)" else "78% (420 km)"
        binding.tvStageOdometer.text = String.format("%,d km", active.odometerKm)

        // Active Service Booking Tracker
        val booking = serviceRepo.getActiveBooking()
        if (booking != null) {
            binding.cardActiveServiceTracker.visibility = View.VISIBLE
            binding.tvTrackerTitle.text = booking.packageTitle
            binding.tvTrackerSubtitle.text = "${booking.workshopName} • ${booking.scheduledDate} ${booking.scheduledTime}"
            binding.tvTrackerBadge.text = booking.status.label
        } else {
            binding.cardActiveServiceTracker.visibility = View.GONE
        }
    }

    private fun setupTrackerCard() {
        binding.cardActiveServiceTracker.setOnClickListener {
            val booking = serviceRepo.getActiveBooking() ?: return@setOnClickListener
            AlertDialog.Builder(requireContext())
                .setTitle("🛠️ Active Service Booking")
                .setMessage("Vehicle: ${booking.vehicleName} (${booking.vehiclePlate})\n" +
                        "Package: ${booking.packageTitle}\n" +
                        "Workshop: ${booking.workshopName}\n" +
                        "Address: ${booking.workshopAddress}\n" +
                        "Scheduled: ${booking.scheduledDate} at ${booking.scheduledTime}\n" +
                        "Total Estimate: ${booking.totalPrice}\n\n" +
                        "Status: ${booking.status.label}")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel Service") { _, _ ->
                    serviceRepo.clearActiveBooking()
                    updateHeaderAndTracker()
                    Toast.makeText(requireContext(), "Service booking cancelled", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    private fun setupMotoristQuickActions() {
        // 1. Vehicle Health
        binding.cardActionCarHealth.setOnClickListener {
            startActivity(Intent(requireContext(), VehicleHealthMonitorActivity::class.java))
        }

        // 2. Book Service
        binding.cardActionBookService.setOnClickListener {
            showServiceBookingModal()
        }

        // 3. Roadside SOS
        binding.cardActionRoadsideSos.setOnClickListener {
            (activity as? MainActivity)?.navigateToTab(R.id.nav_sos)
        }

        // 4. Garages & Towing
        binding.cardActionGarages.setOnClickListener {
            (activity as? MainActivity)?.navigateToTab(R.id.nav_garages)
        }
    }

    private fun showVehicleSwitchModal() {
        val dialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_vehicle, null)
        dialog.setContentView(view)

        val vehicles = vehicleRepo.getVehicles()
        val names = vehicles.map { "${it.vehicleType.emoji} ${it.make} ${it.model} (${it.registrationNumber})" }.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("🚘 Switch Active Vehicle")
            .setItems(names) { _, which ->
                val chosen = vehicles[which]
                vehicleRepo.setDefaultVehicle(chosen.id)
                Toast.makeText(requireContext(), "Active vehicle: ${chosen.make} ${chosen.model}", Toast.LENGTH_SHORT).show()
                updateHeaderAndTracker()
            }
            .setPositiveButton("+ Add New") { _, _ ->
                (activity as? MainActivity)?.navigateToTab(R.id.nav_my_garage)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showServiceBookingModal() {
        val dialog = BottomSheetDialog(requireContext())
        currentBottomSheet = dialog
        val view = layoutInflater.inflate(R.layout.dialog_service_booking, null)
        dialog.setContentView(view)

        val active = vehicleRepo.getActiveVehicle()
        val tvSubtitle = view.findViewById<TextView>(R.id.tvBookingVehicleSubtitle)
        val btnClose = view.findViewById<ImageButton>(R.id.btnCloseBookingDialog)
        val rgPackages = view.findViewById<RadioGroup>(R.id.rgPackages)
        val chipGroupWorkshop = view.findViewById<ChipGroup>(R.id.chipGroupWorkshop)
        val chipGroupSlots = view.findViewById<ChipGroup>(R.id.chipGroupSlots)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirmBooking)

        tvSubtitle.text = "For: ${active.vehicleType.emoji} ${active.make} ${active.model} (${active.registrationNumber})"

        btnClose.setOnClickListener { dialog.dismiss() }

        btnConfirm.setOnClickListener {
            val selectedPackage = when (rgPackages.checkedRadioButtonId) {
                R.id.rbPkgBasic -> ServiceBookingRepository.availablePackages.find { it.id == "pkg_basic" } ?: ServiceBookingRepository.availablePackages.first()
                R.id.rbPkgMajor -> ServiceBookingRepository.availablePackages.find { it.id == "pkg_major" } ?: ServiceBookingRepository.availablePackages.first()
                else -> ServiceBookingRepository.availablePackages.find { it.id == "pkg_periodic" } ?: ServiceBookingRepository.availablePackages.first()
            }

            val workshopName = when (chipGroupWorkshop.checkedChipId) {
                R.id.chipWsNational -> "National Multi-Brand Car Care & Diagnostics"
                R.id.chipWsMotoDoc -> "MotoDoc 2-Wheeler & Superbike Pitstop"
                else -> "Apex 24/7 Auto Rescue & Towing Hub"
            }

            val slotTime = when (chipGroupSlots.checkedChipId) {
                R.id.chipSlotTomorrowMorning -> "Tomorrow, 10:00 AM"
                R.id.chipSlotTomorrowAfternoon -> "Tomorrow, 2:00 PM"
                else -> "Today, 3:30 PM"
            }

            val newBooking = ServiceBooking(
                id = "bk_" + System.currentTimeMillis(),
                vehicleName = "${active.make} ${active.model}",
                vehiclePlate = active.registrationNumber,
                packageTitle = selectedPackage.title,
                workshopName = workshopName,
                workshopAddress = "Highway Service Road",
                scheduledDate = slotTime.substringBefore(","),
                scheduledTime = slotTime.substringAfter(", "),
                totalPrice = selectedPackage.price.substringBefore(" ("),
                status = ServiceStatus.BOOKED
            )

            serviceRepo.createBooking(newBooking)
            dialog.dismiss()
            updateHeaderAndTracker()

            AlertDialog.Builder(requireContext())
                .setTitle("✅ Service Booking Confirmed!")
                .setMessage("Your service for ${newBooking.vehicleName} has been confirmed.\n\n" +
                        "• Package: ${newBooking.packageTitle}\n" +
                        "• Workshop: ${newBooking.workshopName}\n" +
                        "• Schedule: ${slotTime}\n" +
                        "• Total: ${newBooking.totalPrice}\n\n" +
                        "You will receive live status updates in your Pitstop dashboard!")
                .setPositiveButton("View in Dashboard", null)
                .show()
        }

        dialog.show()
    }

    private fun setupViewAllGuides() {
        binding.tvViewAllGuides.setOnClickListener {
            (activity as? MainActivity)?.navigateToTab(R.id.nav_guides)
        }
    }

    private fun setupRecyclerView() {
        issueAdapter = IssueAdapter(emptyList()) { issue ->
            openIssueDetail(issue)
        }
        binding.rvDiagnoseIssues.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiagnoseIssues.adapter = issueAdapter
    }

    private fun setupDiagnosticToolCards() {
        binding.cardToolVisual.setOnClickListener {
            showVisualScanModal()
        }

        binding.cardToolSound.setOnClickListener {
            showSoundScanModal()
        }

        binding.cardToolObd.setOnClickListener {
            showObdLookupModal()
        }
    }

    private fun setupQuickSos() {
        binding.btnQuickSos.setOnClickListener {
            (activity as? MainActivity)?.navigateToTab(R.id.nav_sos)
        }
    }

    private fun refreshIssues() {
        val allIssues = VehicleIssueRepository.getAllIssues()
        // Display top 4 trending / stranded quick fixes for minimal home UI
        val trending = allIssues.take(4)
        issueAdapter.updateIssues(trending)
    }

    // ==========================================
    // MODAL BOTTOM SHEETS FOR DIAGNOSTIC TOOLS
    // ==========================================

    private fun showVisualScanModal() {
        val dialog = BottomSheetDialog(requireContext())
        currentBottomSheet = dialog
        val view = layoutInflater.inflate(R.layout.dialog_visual_scan, null)
        dialog.setContentView(view)

        val btnClose = view.findViewById<ImageButton>(R.id.btnCloseVisualDialog)
        val btnCamera = view.findViewById<Button>(R.id.btnDialogCamera)
        val btnGallery = view.findViewById<Button>(R.id.btnDialogGallery)
        val rvPresets = view.findViewById<RecyclerView>(R.id.rvDialogVisualPresets)

        btnClose.setOnClickListener { dialog.dismiss() }

        btnCamera.setOnClickListener {
            cameraLauncher.launch(null)
        }

        btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        val presetAdapter = ScanPresetAdapter(DiagnosticRepository.visualPresets) { preset ->
            dialog.dismiss()
            handlePresetSelected(preset)
        }
        rvPresets.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvPresets.adapter = presetAdapter

        dialog.show()
    }

    private fun showSoundScanModal() {
        val dialog = BottomSheetDialog(requireContext())
        currentBottomSheet = dialog
        val view = layoutInflater.inflate(R.layout.dialog_sound_scan, null)
        dialog.setContentView(view)

        val btnClose = view.findViewById<ImageButton>(R.id.btnCloseSoundDialog)
        val btnRecord = view.findViewById<Button>(R.id.btnDialogListenAudio)
        val rvPresets = view.findViewById<RecyclerView>(R.id.rvDialogAudioPresets)

        btnClose.setOnClickListener { dialog.dismiss() }

        btnRecord.setOnClickListener {
            btnRecord.text = "🎙️ Listening to Engine Acoustic Profile..."
            btnRecord.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
                val preset = DiagnosticRepository.audioPresets.firstOrNull() ?: return@postDelayed
                showScanMatchDialog(
                    "Audio Acoustic Classifier Result",
                    "Detected Pattern: ${preset.title}\nConfidence: ${preset.confidence}%\nMatched Signal: ${preset.matchedSignals.firstOrNull() ?: "Engine vibration"}",
                    preset.detectedIssueId
                )
            }, 1000)
        }

        val presetAdapter = ScanPresetAdapter(DiagnosticRepository.audioPresets) { preset ->
            dialog.dismiss()
            handlePresetSelected(preset)
        }
        rvPresets.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvPresets.adapter = presetAdapter

        dialog.show()
    }

    private fun showObdLookupModal() {
        val dialog = BottomSheetDialog(requireContext())
        currentBottomSheet = dialog
        val view = layoutInflater.inflate(R.layout.dialog_obd_lookup, null)
        dialog.setContentView(view)

        val btnClose = view.findViewById<ImageButton>(R.id.btnCloseObdDialog)
        val etObd = view.findViewById<EditText>(R.id.etDialogObdCode)
        val btnLookup = view.findViewById<Button>(R.id.btnDialogLookupObd)
        val btnP0300 = view.findViewById<Button>(R.id.btnQuickP0300)
        val btnP0562 = view.findViewById<Button>(R.id.btnQuickP0562)
        val btnP0217 = view.findViewById<Button>(R.id.btnQuickP0217)

        btnClose.setOnClickListener { dialog.dismiss() }

        btnLookup.setOnClickListener {
            val code = etObd.text.toString().trim()
            if (code.isNotEmpty()) {
                dialog.dismiss()
                handleObdLookup(code)
            } else {
                Toast.makeText(requireContext(), "Please enter an OBD code (e.g. P0300)", Toast.LENGTH_SHORT).show()
            }
        }

        btnP0300.setOnClickListener {
            dialog.dismiss()
            handleObdLookup("P0300")
        }

        btnP0562.setOnClickListener {
            dialog.dismiss()
            handleObdLookup("P0562")
        }

        btnP0217.setOnClickListener {
            dialog.dismiss()
            handleObdLookup("P0217")
        }

        dialog.show()
    }

    private fun handlePresetSelected(preset: ScanPreset) {
        val issue = VehicleIssueRepository.getIssueById(preset.detectedIssueId)
        if (issue != null) {
            Toast.makeText(requireContext(), "Selected: ${preset.title} (${preset.confidence}% match)", Toast.LENGTH_SHORT).show()
            openIssueDetail(issue)
        } else {
            Toast.makeText(requireContext(), "${preset.title}: ${preset.description}", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleObdLookup(code: String) {
        val info = DiagnosticRepository.lookupObdCode(code)
        if (info != null) {
            if (info.matchedIssueId != null) {
                val issue = VehicleIssueRepository.getIssueById(info.matchedIssueId)
                if (issue != null) {
                    Toast.makeText(requireContext(), "Found: ${info.title}", Toast.LENGTH_SHORT).show()
                    openIssueDetail(issue)
                    return
                }
            }
            Toast.makeText(requireContext(), "${info.code}: ${info.title}\n${info.description}", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "DTC Code $code: Lookup in progress. Check Fix Guides.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processCapturedImage(source: String) {
        Toast.makeText(requireContext(), "Analyzing $source with AI Vision...", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isAdded) return@postDelayed
            val currentVehicleType = vehicleRepo.getActiveVehicle().vehicleType
            val samplePreset = when (currentVehicleType) {
                VehicleType.TWO_WHEELER -> DiagnosticRepository.visualPresets.find { it.vehicleType == VehicleType.TWO_WHEELER } ?: DiagnosticRepository.visualPresets.first()
                VehicleType.THREE_WHEELER -> DiagnosticRepository.visualPresets.find { it.vehicleType == VehicleType.THREE_WHEELER } ?: DiagnosticRepository.visualPresets.first()
                else -> DiagnosticRepository.visualPresets.find { it.id == "vis_flat_tire" } ?: DiagnosticRepository.visualPresets.first()
            }

            showScanMatchDialog(
                "AI Visual Diagnosis Match",
                "📷 Image Analysis Complete!\n\n" +
                "Detected Defect: ${samplePreset.title}\n" +
                "Confidence Score: ${samplePreset.confidence}%\n\n" +
                "Signals: ${samplePreset.matchedSignals.joinToString(", ")}",
                samplePreset.detectedIssueId
            )
        }, 800)
    }

    private var activeAlertDialog: AlertDialog? = null

    private fun showScanMatchDialog(title: String, message: String, targetIssueId: String) {
        val context = context ?: return
        if (!isAdded || activity?.isFinishing == true) return
        activeAlertDialog?.dismiss()
        activeAlertDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("View Step-by-Step Fix") { _, _ ->
                val issue = VehicleIssueRepository.getIssueById(targetIssueId)
                if (issue != null) {
                    openIssueDetail(issue)
                }
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun openIssueDetail(issue: VehicleIssue) {
        if (!isAdded) return
        val intent = Intent(requireContext(), IssueDetailActivity::class.java).apply {
            putExtra(IssueDetailActivity.EXTRA_ISSUE_ID, issue.id)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchDebounceHandler.removeCallbacksAndMessages(null)
        activeAlertDialog?.dismiss()
        activeAlertDialog = null
        currentBottomSheet?.dismiss()
        currentBottomSheet = null
        _binding = null
    }
}
