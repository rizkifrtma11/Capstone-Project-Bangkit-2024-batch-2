package com.example.rasanusa.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.rasanusa.ui.mainactivity.MainActivity
import com.example.rasanusa.databinding.ActivityLoginBinding
import com.example.rasanusa.helper.AuthenticationHelper
import com.example.rasanusa.ui.customview.EditPasswordCustom
import com.example.rasanusa.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var passwordEditText: EditPasswordCustom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        passwordEditText = binding.etLoginPassword
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        auth = FirebaseAuth.getInstance()

        if (intent.getBooleanExtra("fromRegister", false)) {
            showLoading(false)
        } else {
            checkLogin()
        }

        setupLogin()
        setupView()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

        binding.txtToRegister.setOnClickListener {
            val intentToRegister = Intent(this, RegisterActivity::class.java)
            intentToRegister.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToRegister)
        }
    }

    private fun setupLogin() {
        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    showLoading(false)
                    if (task.isSuccessful) {
                        val username = AuthenticationHelper.getUsername(this, email)

                        Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Gagal login: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkLogin() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            setupLogin()
        }
    }

    private fun setMyButtonEnable() {
        val result = passwordEditText.text
        binding.btnLogin.isEnabled = (result != null) && result.toString().isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        showLoading(false)
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}