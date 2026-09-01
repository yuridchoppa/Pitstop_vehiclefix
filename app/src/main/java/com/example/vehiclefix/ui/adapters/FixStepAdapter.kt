package com.example.vehiclefix.ui.adapters

import android.graphics.Paint
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclefix.data.models.FixStep
import com.example.vehiclefix.databinding.ItemFixStepBinding

class FixStepAdapter(
    private val steps: List<FixStep>,
    private val onStepCompleted: (completedCount: Int, total: Int) -> Unit
) : RecyclerView.Adapter<FixStepAdapter.StepViewHolder>() {

    private val completedSteps = mutableSetOf<Int>()
    private val activeTimers = mutableMapOf<Int, CountDownTimer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = ItemFixStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(steps[position])
    }

    override fun getItemCount(): Int = steps.size

    inner class StepViewHolder(private val binding: ItemFixStepBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(step: FixStep) {
            binding.tvStepNumber.text = step.stepNumber.toString()
            binding.tvStepTitle.text = step.title
            binding.tvStepDescription.text = step.description

            // Pro tip
            if (!step.proTip.isNullOrEmpty()) {
                binding.layoutProTip.visibility = View.VISIBLE
                binding.tvProTip.text = "Tip: ${step.proTip}"
            } else {
                binding.layoutProTip.visibility = View.GONE
            }

            // Warning
            if (!step.warning.isNullOrEmpty()) {
                binding.layoutWarning.visibility = View.VISIBLE
                binding.tvWarning.text = "Warning: ${step.warning}"
            } else {
                binding.layoutWarning.visibility = View.GONE
            }

            // Interactive Timer
            if (step.timerSeconds > 0) {
                binding.btnTimer.visibility = View.VISIBLE
                val mins = step.timerSeconds / 60
                binding.btnTimer.text = "⏱ Start ${mins}m Wait Timer"

                binding.btnTimer.setOnClickListener {
                    startTimer(step.stepNumber, step.timerSeconds)
                }
            } else {
                binding.btnTimer.visibility = View.GONE
            }

            val isDone = completedSteps.contains(step.stepNumber)
            binding.cbStepCompleted.isChecked = isDone
            updateCompletedVisuals(isDone)

            binding.cbStepCompleted.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    completedSteps.add(step.stepNumber)
                } else {
                    completedSteps.remove(step.stepNumber)
                }
                updateCompletedVisuals(checked)
                onStepCompleted(completedSteps.size, steps.size)
            }
        }

        private fun updateCompletedVisuals(done: Boolean) {
            if (done) {
                binding.tvStepTitle.paintFlags = binding.tvStepTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvStepTitle.alpha = 0.5f
                binding.tvStepNumber.setBackgroundColor(0xFF00E676.toInt())
            } else {
                binding.tvStepTitle.paintFlags = binding.tvStepTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvStepTitle.alpha = 1.0f
                binding.tvStepNumber.setBackgroundColor(0xFF263445.toInt())
            }
        }

        private fun startTimer(stepNumber: Int, seconds: Int) {
            activeTimers[stepNumber]?.cancel()
            val totalMillis = seconds * 1000L

            val timer = object : CountDownTimer(totalMillis, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    val remSeconds = millisUntilFinished / 1000
                    val remMinutes = remSeconds / 60
                    val secs = remSeconds % 60
                    binding.btnTimer.text = String.format("⏳ %02d:%02d Remaining", remMinutes, secs)
                    binding.btnTimer.isEnabled = false
                }

                override fun onFinish() {
                    binding.btnTimer.text = "✅ Timer Finished!"
                    binding.btnTimer.isEnabled = true
                    binding.cbStepCompleted.isChecked = true
                }
            }
            activeTimers[stepNumber] = timer
            timer.start()
        }
    }
}
