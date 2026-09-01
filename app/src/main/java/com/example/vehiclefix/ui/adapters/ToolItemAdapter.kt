package com.example.vehiclefix.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefix.data.models.ToolItem
import com.example.vehiclefix.databinding.ItemToolChecklistBinding

class ToolItemAdapter(
    private val tools: List<ToolItem>,
    private val onCheckChanged: () -> Unit
) : RecyclerView.Adapter<ToolItemAdapter.ToolViewHolder>() {

    private val checkedMap = mutableMapOf<String, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolViewHolder {
        val binding = ItemToolChecklistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToolViewHolder, position: Int) {
        holder.bind(tools[position])
    }

    override fun getItemCount(): Int = tools.size

    inner class ToolViewHolder(private val binding: ItemToolChecklistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tool: ToolItem) {
            binding.tvToolName.text = tool.name
            if (!tool.alternative.isNullOrEmpty()) {
                binding.tvToolAlternative.visibility = View.VISIBLE
                binding.tvToolAlternative.text = "Alt: ${tool.alternative}"
            } else {
                binding.tvToolAlternative.visibility = View.GONE
            }

            binding.tvMandatoryBadge.text = if (tool.isMandatory) "REQUIRED" else "OPTIONAL"

            val isChecked = checkedMap[tool.id] ?: false
            binding.cbTool.isChecked = isChecked
            updateStrikeThrough(isChecked)

            binding.cbTool.setOnCheckedChangeListener { _, checked ->
                checkedMap[tool.id] = checked
                updateStrikeThrough(checked)
                onCheckChanged()
            }

            binding.root.setOnClickListener {
                binding.cbTool.isChecked = !binding.cbTool.isChecked
            }
        }

        private fun updateStrikeThrough(checked: Boolean) {
            if (checked) {
                binding.tvToolName.paintFlags = binding.tvToolName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvToolName.alpha = 0.5f
            } else {
                binding.tvToolName.paintFlags = binding.tvToolName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvToolName.alpha = 1.0f
            }
        }
    }
}
