package com.example.vehiclefix.ui.guides

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehiclefix.R
import com.example.vehiclefix.data.models.VehicleType
import com.example.vehiclefix.data.repository.VehicleIssueRepository
import com.example.vehiclefix.databinding.FragmentFixGuidesBinding
import com.example.vehiclefix.ui.adapters.IssueAdapter
import com.example.vehiclefix.ui.issues.IssueDetailActivity

class FixGuidesFragment : Fragment() {

    private var _binding: FragmentFixGuidesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IssueAdapter
    private var selectedType = VehicleType.ALL

    private val searchDebounceHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFixGuidesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupChips()
        setupSearch()
        refreshList()
    }

    private fun setupRecyclerView() {
        adapter = IssueAdapter(emptyList()) { issue ->
            val intent = Intent(requireContext(), IssueDetailActivity::class.java).apply {
                putExtra(IssueDetailActivity.EXTRA_ISSUE_ID, issue.id)
            }
            startActivity(intent)
        }
        binding.rvFixGuidesList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFixGuidesList.adapter = adapter
    }

    private fun setupChips() {
        binding.chipGroupGuidesVehicle.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedType = when {
                checkedIds.contains(R.id.chipGuidesThreeWheeler) -> VehicleType.THREE_WHEELER
                checkedIds.contains(R.id.chipGuidesCar) -> VehicleType.CAR
                checkedIds.contains(R.id.chipGuidesBike) -> VehicleType.TWO_WHEELER
                else -> VehicleType.ALL
            }
            refreshList()
        }
    }

    private fun setupSearch() {
        binding.etSearchGuides.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchRunnable?.let { searchDebounceHandler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    refreshList()
                }
                searchDebounceHandler.postDelayed(searchRunnable!!, 150)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun refreshList() {
        val query = binding.etSearchGuides.text?.toString()?.trim().orEmpty()
        val list = VehicleIssueRepository.searchIssues(query, selectedType)
        adapter.updateIssues(list)
        binding.tvGuidesCount.text = "${list.size} Guides Available:"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchRunnable?.let { searchDebounceHandler.removeCallbacks(it) }
        _binding = null
    }
}
