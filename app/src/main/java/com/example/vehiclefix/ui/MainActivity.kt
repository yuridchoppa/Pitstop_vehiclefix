package com.example.vehiclefix.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vehiclefix.R
import com.example.vehiclefix.databinding.ActivityMainBinding
import com.example.vehiclefix.ui.diagnose.DiagnoseFragment
import com.example.vehiclefix.ui.emergency.EmergencySosFragment
import com.example.vehiclefix.ui.garages.GaragesFragment
import com.example.vehiclefix.ui.guides.FixGuidesFragment
import com.example.vehiclefix.ui.mygarage.MyGarageFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentTabId = R.id.nav_diagnose

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentTabId = savedInstanceState?.getInt("active_tab_id") ?: R.id.nav_diagnose

        // Only load the initial fragment on fresh startup;
        // On activity recreation (such as theme switch), FragmentManager automatically restores the fragment.
        if (savedInstanceState == null) {
            loadTabFragment(currentTabId)
        }

        binding.bottomNavigation.selectedItemId = currentTabId

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (currentTabId != item.itemId) {
                currentTabId = item.itemId
                loadTabFragment(item.itemId)
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("active_tab_id", currentTabId)
    }

    private fun loadTabFragment(tabId: Int) {
        val fragment: Fragment = when (tabId) {
            R.id.nav_diagnose -> DiagnoseFragment()
            R.id.nav_guides -> FixGuidesFragment()
            R.id.nav_garages -> GaragesFragment()
            R.id.nav_sos -> EmergencySosFragment()
            R.id.nav_my_garage -> MyGarageFragment()
            else -> DiagnoseFragment()
        }
        loadFragment(fragment)
    }

    fun navigateToTab(navItemId: Int) {
        currentTabId = navItemId
        binding.bottomNavigation.selectedItemId = navItemId
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
