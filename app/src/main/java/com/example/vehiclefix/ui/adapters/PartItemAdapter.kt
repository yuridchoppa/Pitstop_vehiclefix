package com.example.vehiclefix.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefix.data.models.PartItem
import com.example.vehiclefix.databinding.ItemPartChecklistBinding

class PartItemAdapter(
    private val parts: List<PartItem>,
    private val onCheckChanged: () -> Unit
) : RecyclerView.Adapter<PartItemAdapter.PartViewHolder>() {

    private val checkedMap = mutableMapOf<String, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartViewHolder {
        val binding = ItemPartChecklistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartViewHolder, position: Int) {
        holder.bind(parts[position])
    }

    override fun getItemCount(): Int = parts.size

    inner class PartViewHolder(private val binding: ItemPartChecklistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(part: PartItem) {
            binding.tvPartName.text = part.name
            binding.tvPartPrice.text = "Approx: ${part.approxPrice}"

            val isChecked = checkedMap[part.id] ?: false
            binding.cbPart.isChecked = isChecked
            updateStrikeThrough(isChecked)

            binding.cbPart.setOnCheckedChangeListener { _, checked ->
                checkedMap[part.id] = checked
                updateStrikeThrough(checked)
                onCheckChanged()
            }

            binding.root.setOnClickListener {
                binding.cbPart.isChecked = !binding.cbPart.isChecked
            }
        }

        private fun updateStrikeThrough(checked: Boolean) {
            if (checked) {
                binding.tvPartName.paintFlags = binding.tvPartName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvPartName.alpha = 0.5f
            } else {
                binding.tvPartName.paintFlags = binding.tvPartName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvPartName.alpha = 1.0f
            }
        }
    }
}
