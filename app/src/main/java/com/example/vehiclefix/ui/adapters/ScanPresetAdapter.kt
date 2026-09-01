package com.example.vehiclefix.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefix.data.repository.ScanPreset
import com.example.vehiclefix.databinding.ItemScanPresetBinding

class ScanPresetAdapter(
    private val presets: List<ScanPreset>,
    private val onPresetSelected: (ScanPreset) -> Unit
) : RecyclerView.Adapter<ScanPresetAdapter.PresetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresetViewHolder {
        val binding = ItemScanPresetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PresetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PresetViewHolder, position: Int) {
        holder.bind(presets[position])
    }

    override fun getItemCount(): Int = presets.size

    inner class PresetViewHolder(private val binding: ItemScanPresetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(preset: ScanPreset) {
            binding.tvPresetEmoji.text = preset.iconEmoji
            binding.tvPresetConfidence.text = "${preset.confidence}% Match"
            binding.tvPresetTitle.text = preset.title
            binding.tvPresetDescription.text = preset.description

            binding.root.setOnClickListener {
                onPresetSelected(preset)
            }
        }
    }
}
