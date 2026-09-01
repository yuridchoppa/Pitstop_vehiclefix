package com.example.vehiclefix.ui.emergency

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vehiclefix.databinding.FragmentEmergencySosBinding

class EmergencySosFragment : Fragment() {

    private var _binding: FragmentEmergencySosBinding? = null
    private val binding get() = _binding!!

    private var isFlasherActive = false
    private var isTorchActive = false
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null

    private val simulatedLatitude = 28.6139
    private val simulatedLongitude = 77.2090
    private val simulatedLocationName = "NH-44 Bypass Highway, Milestone 42"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencySosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCameraTorch()
        setupHazardFlasher()
        setupTorchToggle()
        setupLocationSharing()
        setupHelplines()
    }

    private fun initCameraTorch() {
        try {
            cameraManager = requireContext().getSystemService(Context.CAMERA_SERVICE) as? CameraManager
            cameraId = cameraManager?.cameraIdList?.firstOrNull()
        } catch (_: Exception) {}
    }

    private fun setupHazardFlasher() {
        binding.cardHazardFlasher.setOnClickListener {
            isFlasherActive = !isFlasherActive
            if (isFlasherActive) {
                binding.cardHazardFlasher.setCardBackgroundColor(Color.parseColor("#FF6D00"))
                binding.tvFlasherState.text = "Hazard (ON)"
                binding.tvFlasherState.setTextColor(Color.parseColor("#000000"))
                binding.tvFlasherSub.text = "Tap to Turn OFF"
                binding.tvFlasherSub.setTextColor(Color.parseColor("#1A1A1A"))
                binding.tvFlasherIcon.text = "🚨"
            } else {
                binding.cardHazardFlasher.setCardBackgroundColor(Color.parseColor("#1F2631"))
                binding.tvFlasherState.text = "Hazard Screen"
                binding.tvFlasherState.setTextColor(Color.parseColor("#F0F6FC"))
                binding.tvFlasherSub.text = "Tap to Toggle Amber"
                binding.tvFlasherSub.setTextColor(Color.parseColor("#FF6D00"))
                binding.tvFlasherIcon.text = "💡"
            }
        }
    }

    private fun setupTorchToggle() {
        binding.cardTorch.setOnClickListener {
            isTorchActive = !isTorchActive
            toggleTorch(isTorchActive)
        }
    }

    private fun toggleTorch(enable: Boolean) {
        try {
            if (cameraId != null && cameraManager != null) {
                cameraManager?.setTorchMode(cameraId!!, enable)
            }
            if (enable) {
                binding.tvTorchState.text = "Torch (ON)"
                binding.tvTorchSub.text = "Tap to Turn OFF"
                binding.cardTorch.setCardBackgroundColor(Color.parseColor("#0099CC"))
            } else {
                binding.tvTorchState.text = "Flashlight"
                binding.tvTorchSub.text = "Tap to Toggle Torch"
                binding.cardTorch.setCardBackgroundColor(Color.parseColor("#1F2631"))
            }
        } catch (_: Exception) {
            Toast.makeText(requireContext(), if (enable) "Flashlight ON" else "Flashlight OFF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupLocationSharing() {
        val locationText = "Lat: $simulatedLatitude° N, Lon: $simulatedLongitude° E\nNear $simulatedLocationName"
        binding.tvGpsCoordinates.text = locationText

        // Copy Location
        binding.btnCopyGps.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "Pitstop GPS Location",
                "https://maps.google.com/?q=$simulatedLatitude,$simulatedLongitude ($simulatedLocationName)"
            )
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "📍 GPS Location copied to clipboard!", Toast.LENGTH_SHORT).show()
        }

        // Broadcast SOS
        binding.btnShareSos.setOnClickListener {
            val shareMessage = "🚨 *PITSTOP EMERGENCY BREAKDOWN SOS* 🚨\n" +
                    "I am stranded with my vehicle and require immediate roadside assistance.\n" +
                    "📍 Location: $simulatedLocationName\n" +
                    "🗺️ GPS Map Link: https://maps.google.com/?q=$simulatedLatitude,$simulatedLongitude\n" +
                    "Sent via Pitstop Roadside Assistance App"

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "EMERGENCY: Pitstop Roadside SOS")
                putExtra(Intent.EXTRA_TEXT, shareMessage)
            }
            startActivity(Intent.createChooser(shareIntent, "Broadcast Roadside SOS via..."))
        }
    }

    private fun setupHelplines() {
        // NHAI Helpline 1033
        binding.btnCall1033.setOnClickListener { dialNumber("1033") }
        binding.cardHighwayHelpline.setOnClickListener { dialNumber("1033") }

        // Police / Emergency 112
        binding.btnCall112.setOnClickListener { dialNumber("112") }
        binding.cardPoliceHelpline.setOnClickListener { dialNumber("112") }
    }

    private fun dialNumber(number: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(requireContext(), "Unable to dial $number", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isTorchActive) {
            toggleTorch(false)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isTorchActive) {
            toggleTorch(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isTorchActive) {
            toggleTorch(false)
        }
        cameraManager = null
        cameraId = null
        _binding = null
    }
}
