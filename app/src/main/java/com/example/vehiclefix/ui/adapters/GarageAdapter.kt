package com.example.vehiclefix.ui.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefix.data.models.Garage
import com.example.vehiclefix.databinding.ItemGarageCardBinding

class GarageAdapter(
    private var garages: List<Garage>,
    private val selectedIssueTitle: String? = null,
    private val selectedVehicleType: String? = null
) : RecyclerView.Adapter<GarageAdapter.GarageViewHolder>() {

    fun updateGarages(newGarages: List<Garage>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = garages.size
            override fun getNewListSize(): Int = newGarages.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return garages[oldItemPosition].id == newGarages[newItemPosition].id
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return garages[oldItemPosition] == newGarages[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        garages = newGarages
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarageViewHolder {
        val binding = ItemGarageCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GarageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GarageViewHolder, position: Int) {
        holder.bind(garages[position])
    }

    override fun getItemCount(): Int = garages.size

    inner class GarageViewHolder(private val binding: ItemGarageCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(garage: Garage) {
            val context = binding.root.context

            binding.tvGarageName.text = garage.name
            binding.tvGarageAddress.text = garage.address
            binding.tvDistance.text = "📍 ${garage.distanceKm} km"
            binding.tvRating.text = "⭐ ${garage.rating} (${garage.reviewCount})"

            // Specialties badge
            val topSpecialty = garage.specialties.firstOrNull()
            binding.tvSpecialtyBadge.text = "${topSpecialty?.emoji ?: "🔧"} ${topSpecialty?.displayName ?: "Garage"}"

            // Tow truck badge
            if (garage.towTruckAvailable) {
                binding.tvTowBadge.visibility = View.VISIBLE
                binding.tvTowBadge.text = "Tow (~${garage.estimatedArrivalMins}m)"
            } else if (garage.is24x7) {
                binding.tvTowBadge.visibility = View.VISIBLE
                binding.tvTowBadge.text = "24/7 Open"
            } else {
                binding.tvTowBadge.visibility = View.GONE
            }

            binding.tvPricing.text = garage.pricingEstimate

            // Phone Call Intent
            binding.btnCall.setOnClickListener {
                try {
                    val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse(garage.getDialUri()))
                    context.startActivity(dialIntent)
                } catch (_: Exception) {
                    Toast.makeText(context, "Unable to dial: ${garage.phone}", Toast.LENGTH_SHORT).show()
                }
            }

            // WhatsApp Intent with Pre-filled SOS message
            binding.btnWhatsApp.setOnClickListener {
                try {
                    val issueMsg = if (!selectedIssueTitle.isNullOrEmpty()) " Issue: $selectedIssueTitle." else ""
                    val vehicleMsg = if (!selectedVehicleType.isNullOrEmpty()) " Vehicle: $selectedVehicleType." else ""
                    val sosMessage = "Hello ${garage.name}, I found your garage on Pitstop. I need mechanical assistance.$vehicleMsg$issueMsg Location: Approx ${garage.distanceKm} km away. Please respond!"
                    
                    val uri = Uri.parse(garage.getWhatsAppUri(sosMessage))
                    val waIntent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(waIntent)
                } catch (_: Exception) {
                    Toast.makeText(context, "Calling ${garage.phone} instead...", Toast.LENGTH_SHORT).show()
                    val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse(garage.getDialUri()))
                    context.startActivity(dialIntent)
                }
            }

            // Google Maps Directions Intent
            binding.btnDirections.setOnClickListener {
                try {
                    val mapUri = Uri.parse(garage.getMapsUri())
                    val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                    context.startActivity(mapIntent)
                } catch (_: Exception) {
                    val webMapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=${garage.latitude},${garage.longitude}"))
                    context.startActivity(webMapIntent)
                }
            }
        }
    }
}
