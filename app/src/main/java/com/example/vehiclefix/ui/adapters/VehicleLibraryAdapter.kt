package com.example.vehiclefix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefix.data.repository.LibraryVehicle
import com.example.vehiclefix.databinding.ItemLibraryVehicleCardBinding

class VehicleLibraryAdapter(
    private var vehicles: List<LibraryVehicle>,
    private val onAddToGarageClick: (LibraryVehicle) -> Unit,
    private val onViewSpecsClick: (LibraryVehicle) -> Unit
) : RecyclerView.Adapter<VehicleLibraryAdapter.LibraryViewHolder>() {

    fun updateVehicles(newVehicles: List<LibraryVehicle>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = vehicles.size
            override fun getNewListSize(): Int = newVehicles.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return vehicles[oldPos].id == newVehicles[newPos].id
            }
            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                return vehicles[oldPos] == newVehicles[newPos]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        vehicles = newVehicles
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding = ItemLibraryVehicleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    override fun getItemCount(): Int = vehicles.size

    inner class LibraryViewHolder(private val binding: ItemLibraryVehicleCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicle: LibraryVehicle) {
            binding.tvLibVehicleEmoji.text = vehicle.emoji
            binding.tvLibVehicleTitle.text = "${vehicle.make} ${vehicle.model}"
            binding.tvLibVehicleCategory.text = "${vehicle.category} • ${vehicle.fuelType}"
            binding.tvLibFuelBadge.text = vehicle.fuelType

            binding.tvLibEngineSpec.text = "${vehicle.engineDisplacement} (${vehicle.powerBhp.substringBefore(" @")})"
            binding.tvLibOilSpec.text = vehicle.oilGrade.substringBefore(" (")
            binding.tvLibTirePsi.text = vehicle.tirePressurePsi.substringBefore(" (")
            binding.tvLibServiceInterval.text = vehicle.serviceIntervalKm

            binding.btnLibAddGarage.setOnClickListener {
                onAddToGarageClick(vehicle)
            }

            binding.btnLibViewSpecs.setOnClickListener {
                onViewSpecsClick(vehicle)
            }
        }
    }
}
