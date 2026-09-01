package com.example.vehiclefix.ui.issues

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehiclefix.R
import com.example.vehiclefix.data.models.Severity
import com.example.vehiclefix.data.models.VehicleIssue
import com.example.vehiclefix.data.repository.VehicleIssueRepository
import com.example.vehiclefix.databinding.ActivityIssueDetailBinding
import com.example.vehiclefix.ui.adapters.FixStepAdapter
import com.example.vehiclefix.ui.adapters.PartItemAdapter
import com.example.vehiclefix.ui.adapters.ToolItemAdapter
import com.example.vehiclefix.ui.garages.GarageListActivity

class IssueDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ISSUE_ID = "extra_issue_id"
    }

    private lateinit var binding: ActivityIssueDetailBinding
    private var currentIssue: VehicleIssue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIssueDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val issueId = intent.getStringExtra(EXTRA_ISSUE_ID)
        currentIssue = VehicleIssueRepository.getIssueById(issueId ?: "")

        if (currentIssue == null) {
            Toast.makeText(this, "Issue not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupToolbar()
        bindIssueData(currentIssue!!)
        setupTabs()
        setupTriageAction(currentIssue!!)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun bindIssueData(issue: VehicleIssue) {
        binding.tvVehicleBadge.text = "${issue.vehicleType.emoji} ${issue.vehicleType.displayName}"
        binding.tvCategoryBadge.text = issue.category
        binding.tvIssueTitle.text = issue.title
        binding.tvIssueSummary.text = issue.summary
        binding.tvEstimatedTime.text = "⏱ Est. Time: ${issue.estimatedTime}"

        // Difficulty badge
        binding.tvDifficulty.text = issue.difficulty.label
        try {
            binding.tvDifficulty.setTextColor(Color.parseColor(issue.difficulty.badgeColorHex))
        } catch (e: Exception) {
            // Default fallback
        }

        // Severity badge
        binding.tvSeverityBadge.text = issue.severity.name.replace("_", " ")
        try {
            binding.tvSeverityBadge.setTextColor(Color.parseColor(issue.severity.colorHex))
        } catch (e: Exception) {
            // Default fallback
        }

        // Symptoms text
        val symptomsFormatted = issue.symptoms.joinToString(separator = "\n") { "• $it" }
        binding.tvSymptomsList.text = symptomsFormatted

        // Causes text
        val causesFormatted = issue.commonCauses.joinToString(separator = "\n") { "• $it" }
        binding.tvCausesList.text = causesFormatted

        // Safety precautions text
        val safetyFormatted = issue.safetyPrecautions.joinToString(separator = "\n") { "⚠️ $it" }
        binding.tvSafetyList.text = safetyFormatted

        // Setup Tools Checklist
        val toolAdapter = ToolItemAdapter(issue.toolsNeeded) {
            // Callback when check changed
        }
        binding.rvToolsChecklist.layoutManager = LinearLayoutManager(this)
        binding.rvToolsChecklist.adapter = toolAdapter

        // Setup Parts Checklist
        val partAdapter = PartItemAdapter(issue.partsNeeded) {
            // Callback when check changed
        }
        binding.rvPartsChecklist.layoutManager = LinearLayoutManager(this)
        binding.rvPartsChecklist.adapter = partAdapter

        // Setup Steps List
        val stepAdapter = FixStepAdapter(issue.steps) { doneCount, total ->
            binding.tvStepsProgressHeader.text = "Interactive Fix Steps ($doneCount/$total done)"
            binding.pbSteps.max = total
            binding.pbSteps.progress = doneCount
            if (doneCount == total && total > 0) {
                Toast.makeText(this, "🎉 All DIY Fix steps completed successfully!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvFixSteps.layoutManager = LinearLayoutManager(this)
        binding.rvFixSteps.adapter = stepAdapter
        binding.pbSteps.max = issue.steps.size
        binding.pbSteps.progress = 0

        // Highlight triage banner if difficult or critical
        if (issue.difficulty.isProRecommended || issue.severity == Severity.CRITICAL_STRANDED) {
            binding.tvTriageTitle.text = "⚠️ Professional Repair Strongly Recommended"
            binding.tvTriageDescription.text = "This issue has high risk of breakdown or requires specialist tools. Tap below to find verified mechanics nearby."
        }
    }

    private fun setupTabs() {
        binding.toggleDetailSections.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnTabOverview -> {
                        binding.layoutOverviewContent.visibility = View.VISIBLE
                        binding.layoutThingsNeededContent.visibility = View.GONE
                        binding.layoutStepsContent.visibility = View.GONE
                    }
                    R.id.btnTabThingsNeeded -> {
                        binding.layoutOverviewContent.visibility = View.GONE
                        binding.layoutThingsNeededContent.visibility = View.VISIBLE
                        binding.layoutStepsContent.visibility = View.GONE
                    }
                    R.id.btnTabSteps -> {
                        binding.layoutOverviewContent.visibility = View.GONE
                        binding.layoutThingsNeededContent.visibility = View.GONE
                        binding.layoutStepsContent.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupTriageAction(issue: VehicleIssue) {
        binding.btnFindGaragesForIssue.setOnClickListener {
            val intent = Intent(this, GarageListActivity::class.java).apply {
                putExtra(GarageListActivity.EXTRA_VEHICLE_TYPE, issue.vehicleType.name)
                putExtra(GarageListActivity.EXTRA_ISSUE_TITLE, issue.title)
            }
            startActivity(intent)
        }
    }
}
