package com.example.vehiclefix.ui.garages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehiclefix.data.models.VehicleType
import com.example.vehiclefix.data.repository.GarageRepository
import com.example.vehiclefix.databinding.ActivityGarageListBinding
import com.example.vehiclefix.ui.adapters.GarageAdapter

class GarageListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_VEHICLE_TYPE = "extra_vehicle_type"
        const val EXTRA_ISSUE_TITLE = "extra_issue_title"
    }

    private lateinit var binding: ActivityGarageListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGarageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vehicleTypeName = intent.getStringExtra(EXTRA_VEHICLE_TYPE)
        val issueTitle = intent.getStringExtra(EXTRA_ISSUE_TITLE)

        val vehicleType = try {
            VehicleType.valueOf(vehicleTypeName ?: "ALL")
        } catch (e: Exception) {
            VehicleType.ALL
        }

        setupToolbar()
        bindContext(vehicleType, issueTitle)
        setupGaragesList(vehicleType, issueTitle)
    }

    private fun setupToolbar() {
        binding.toolbarGarageList.setNavigationOnClickListener {
            finish()
        }
    }

    private fun bindContext(vehicleType: VehicleType, issueTitle: String?) {
        val issueDesc = issueTitle ?: "General Breakdown"
        binding.tvTargetVehicleAndIssue.text = "Showing Garages for ${vehicleType.emoji} ${vehicleType.displayName} • Issue: $issueDesc"
    }

    private fun setupGaragesList(vehicleType: VehicleType, issueTitle: String?) {
        val garages = GarageRepository.getGaragesForVehicleType(vehicleType)
        val adapter = GarageAdapter(
            garages = garages,
            selectedIssueTitle = issueTitle,
            selectedVehicleType = "${vehicleType.emoji} ${vehicleType.displayName}"
        )
        binding.rvTargetedGarages.layoutManager = LinearLayoutManager(this)
        binding.rvTargetedGarages.adapter = adapter
    }
}
