package com.example.vehiclefix.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vehiclefix.R
import com.example.vehiclefix.data.repository.AuthRepository
import com.example.vehiclefix.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var authRepo: AuthRepository
    private var isLoginMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepo = AuthRepository(this)

        setupToggle()
        setupButtons()
    }

    private fun setupToggle() {
        binding.toggleAuthMode.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                isLoginMode = (checkedId == R.id.btnTabLogin)
                updateFormMode()
            }
        }
    }

    private fun updateFormMode() {
        if (isLoginMode) {
            binding.tilName.visibility = View.GONE
            binding.tilPhone.visibility = View.GONE
            binding.btnSubmitAuth.text = "Log In"
            binding.tvAuthSubtitle.text = "Welcome back! Access your garage & diagnostics"
        } else {
            binding.tilName.visibility = View.VISIBLE
            binding.tilPhone.visibility = View.VISIBLE
            binding.btnSubmitAuth.text = "Create Free Account"
            binding.tvAuthSubtitle.text = "Register for instant roadside assistance & telemetry"
        }
    }

    private fun setupButtons() {
        binding.btnCloseAuth.setOnClickListener {
            finish()
        }

        binding.btnGuestMode.setOnClickListener {
            Toast.makeText(this, "Continuing as Guest", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnDemoLogin.setOnClickListener {
            val user = authRepo.login(
                email = "alex.driver@pitstop.io",
                name = "Alex Driver",
                phone = "+91 98765 43210"
            )
            Toast.makeText(this, "⚡ Logged in as ${user.name}", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }

        binding.btnSubmitAuth.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString()?.trim().orEmpty()
        val name = binding.etName.text?.toString()?.trim().orEmpty()
        val phone = binding.etPhone.text?.toString()?.trim().orEmpty()

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Please enter a valid email address"
            return
        } else {
            binding.tilEmail.error = null
        }

        if (password.length < 6) {
            binding.tilPassword.error = "Password must be at least 6 characters"
            return
        } else {
            binding.tilPassword.error = null
        }

        if (!isLoginMode && name.isEmpty()) {
            binding.tilName.error = "Please enter your name"
            return
        } else {
            binding.tilName.error = null
        }

        val user = authRepo.login(email, if (isLoginMode) null else name, if (isLoginMode) null else phone)
        val welcomeMsg = if (isLoginMode) "Welcome back, ${user.name}!" else "Account created! Welcome to Pitstop, ${user.name}!"
        Toast.makeText(this, welcomeMsg, Toast.LENGTH_SHORT).show()

        setResult(RESULT_OK)
        finish()
    }
}
