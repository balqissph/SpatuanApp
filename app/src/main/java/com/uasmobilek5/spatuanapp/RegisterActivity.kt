package com.uasmobilek5.spatuanapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val ivTogglePassword = findViewById<ImageView>(R.id.ivTogglePassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvToRegister = findViewById<TextView>(R.id.tvToRegister)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.eye_open)
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.eye_closed)
            }
            etPassword.setSelection(etPassword.text.length)
        }

        val tvSpatuan = findViewById<TextView>(R.id.tvSpatuan)

        val text = "SPATUAN"
        val spannable = SpannableString(text)
        spannable.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            0, 3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.WHITE),
            3, 7,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvSpatuan.text = spannable

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                 startActivity(Intent(this, LoginActivity::class.java))
                 finish()
            }
        }

        tvToRegister.setOnClickListener {
            finish()
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}
