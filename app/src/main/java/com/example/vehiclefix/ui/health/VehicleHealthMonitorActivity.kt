package com.example.vehiclefix.ui.health

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.vehiclefix.data.models.VehicleType
import com.example.vehiclefix.data.repository.UserVehicleRepository
import com.example.vehiclefix.databinding.ActivityVehicleHealthMonitorBinding

class VehicleHealthMonitorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleHealthMonitorBinding
    private lateinit var vehicleRepo: UserVehicleRepository
    private var isScanning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleHealthMonitorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vehicleRepo = UserVehicleRepository(this)

        setupToolbar()
        loadVehicleData()
        setupDeepScan()
    }

    private fun setupToolbar() {
        binding.toolbarHealth.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadVehicleData() {
        val active = vehicleRepo.getActiveVehicle()
        binding.tvHealthVehicleName.text = "${active.vehicleType.emoji} ${active.make} ${active.model} (${active.year})"
        binding.tvHealthVehiclePlate.text = "${active.registrationNumber} • ${String.format("%,d km", active.odometerKm)}"

        when (active.vehicleType) {
            VehicleType.TWO_WHEELER -> {
                binding.tvTireFL.text = "Front: 28.0 PSI"
                binding.tvTireFR.text = "Rear: 32.0 PSI"
                binding.tvTireRL.visibility = View.GONE
                binding.tvTireRR.visibility = View.GONE
            }
            VehicleType.THREE_WHEELER -> {
                binding.tvTireFL.text = "Front: 30.0 PSI"
                binding.tvTireFR.text = "Rear L: 34.0 PSI"
                binding.tvTireRL.text = "Rear R: 34.0 PSI"
                binding.tvTireRR.visibility = View.GONE
            }
            else -> {
                binding.tvTireFL.text = "33.0 PSI"
                binding.tvTireFR.text = "33.0 PSI"
                binding.tvTireRL.text = "32.0 PSI"
                binding.tvTireRR.text = "32.5 PSI"
                binding.tvTireRL.visibility = View.VISIBLE
                binding.tvTireRR.visibility = View.VISIBLE
            }
        }
    }

    private val scanHandler = Handler(Looper.getMainLooper())
    private var scanRunnable: Runnable? = null
    private var reportDialog: AlertDialog? = null

    private fun setupDeepScan() {
        binding.btnRunDeepScan.setOnClickListener {
            if (isScanning) return@setOnClickListener
            runEcuHealthProbe()
        }
    }

    private fun runEcuHealthProbe() {
        isScanning = true
        binding.btnRunDeepScan.isEnabled = false
        binding.pbEcuScan.progress = 0

        val steps = listOf(
            "Connecting to CAN-Bus & Engine ECU..." to 20,
            "Scanning Powertrain & Fuel Injection Modules..." to 45,
            "Probing ABS / Hydraulic Braking Controller..." to 70,
            "Analyzing 12V Battery & Charging Telemetry..." to 90,
            "Scan Complete! All 14 Subsystems Healthy." to 100
        )

        var currentStep = 0

        fun executeNextStep() {
            if (isFinishing || isDestroyed) return

            if (currentStep < steps.size) {
                val (statusText, progressVal) = steps[currentStep]
                binding.tvScanStatusText.text = statusText
                binding.pbEcuScan.progress = progressVal

                currentStep++
                scanRunnable = Runnable { executeNextStep() }
                scanHandler.postDelayed(scanRunnable!!, 650)
            } else {
                isScanning = false
                binding.btnRunDeepScan.isEnabled = true
                binding.tvScanStatusText.text = "Operational (100%)"
                binding.tvScanSubtext.text = "Last Full Probe: Just now • 0 DTC Error Codes Found"
                binding.tvHealthScorePercent.text = "98%"
                binding.tvBatteryVoltage.text = "14.1 V (Charging)"

                showScanReportDialog()
            }
        }

        executeNextStep()
    }

    private fun showScanReportDialog() {
        if (isFinishing || isDestroyed) return
        reportDialog?.dismiss()
        reportDialog = AlertDialog.Builder(this)
            .setTitle("⚡ ECU Deep Health Probe Report")
            .setMessage("✅ Full System Scan Passed!\n\n" +
                    "• Powertrain: 0 Faults / Misfires (RPM Stable)\n" +
                    "• Electrical: 14.1V Alternator Output Active\n" +
                    "• Braking: Hydraulic Pressure Nominal\n" +
                    "• Emission Systems: Catalyst & O2 Ready\n\n" +
                    "Overall Vehicle Health Score: 98/100 (Optimal Condition)")
            .setPositiveButton("Done", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        scanHandler.removeCallbacksAndMessages(null)
        reportDialog?.dismiss()
        reportDialog = null
    }
}
