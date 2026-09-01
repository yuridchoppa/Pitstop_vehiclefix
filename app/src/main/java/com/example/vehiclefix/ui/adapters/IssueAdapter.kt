package com.example.vehiclefix.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefix.data.models.Severity
import com.example.vehiclefix.data.models.VehicleIssue
import com.example.vehiclefix.databinding.ItemIssueCardBinding

class IssueAdapter(
    private var issues: List<VehicleIssue>,
    private val onIssueClick: (VehicleIssue) -> Unit
) : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    fun updateIssues(newIssues: List<VehicleIssue>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = issues.size
            override fun getNewListSize(): Int = newIssues.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return issues[oldItemPosition].id == newIssues[newItemPosition].id
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return issues[oldItemPosition] == newIssues[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        issues = newIssues
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding = ItemIssueCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(issues[position])
    }

    override fun getItemCount(): Int = issues.size

    inner class IssueViewHolder(private val binding: ItemIssueCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: VehicleIssue) {
            binding.tvVehicleTypeBadge.text = "${issue.vehicleType.emoji} ${issue.vehicleType.displayName}"
            binding.tvCategoryBadge.text = issue.category
            binding.tvIssueTitle.text = issue.title
            binding.tvIssueSummary.text = issue.summary
            binding.tvEstimatedTime.text = "⏱ ${issue.estimatedTime}"

            // Difficulty
            binding.tvDifficultyBadge.text = issue.difficulty.label
            try {
                binding.tvDifficultyBadge.setTextColor(Color.parseColor(issue.difficulty.badgeColorHex))
            } catch (_: Exception) {}

            // Severity
            binding.tvSeverityBadge.text = issue.severity.name.replace("_", " ")
            try {
                binding.tvSeverityBadge.setTextColor(Color.parseColor(issue.severity.colorHex))
            } catch (_: Exception) {}

            // Stranded Hazard badge
            if (issue.isStrandedHazard || issue.severity == Severity.CRITICAL_STRANDED) {
                binding.tvStrandedWarning.visibility = View.VISIBLE
            } else {
                binding.tvStrandedWarning.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onIssueClick(issue)
            }
        }
    }
}
