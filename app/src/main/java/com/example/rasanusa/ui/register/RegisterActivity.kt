package com.example.rasanusa.ui.register

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
import com.example.rasanusa.R
import com.example.rasanusa.databinding.ActivityRegisterBinding
import com.example.rasanusa.helper.AuthenticationHelper
import com.example.rasanusa.ui.customview.EditPasswordCustom
import com.example.rasanusa.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var passwordEditText: EditPasswordCustom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupRegister()

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

        binding.txtToLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            intentToLogin.putExtra("fromRegister", true)
            startActivity(intentToLogin)
            finish()
        }
    }

    private fun setupRegister() {
        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            val username = binding.etRegisterUsername.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                showLoading(true)
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    showLoading(false)
                    if (task.isSuccessful) {
                        AuthenticationHelper.saveUsername(this, email, username)
                        Toast.makeText(this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthException) {
                            when (exception.errorCode) {
                                "ERROR_WEAK_PASSWORD" -> {
                                    Toast.makeText(this,
                                        getString(R.string.password_terlalu_lemah_gunakan_minimal_6_karakter), Toast.LENGTH_SHORT).show()
                                }

                                "ERROR_EMAIL_ALREADY_IN_USE" -> {
                                    Toast.makeText(this,
                                        getString(R.string.email_sudah_terdaftar_gunakan_email_lain), Toast.LENGTH_SHORT).show()
                                }

                                "ERROR_INVALID_EMAIL" -> {
                                    Toast.makeText(this, getString(R.string.email_invalid), Toast.LENGTH_SHORT).show()
                                }

                                else -> {
                                    Toast.makeText(this,
                                        getString(R.string.gagal_membuat_akun, exception.message), Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            if (exception != null) {
                                Toast.makeText(this,
                                    getString(R.string.gagal_membuat_akun, exception.message), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.semua_field_harus_diisi), Toast.LENGTH_SHORT).show()
            }
        }


        passwordEditText = binding.etRegisterPassword
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setMyButtonEnable() {
        val result = passwordEditText.text
        binding.btnRegister.isEnabled = (result != null) && result.toString().isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        showLoading(false)
    }
}
