package com.uasmobilek5.spatuanapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val ivTogglePassword = findViewById<ImageView>(R.id.ivTogglePassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvToRegister = findViewById<TextView>(R.id.tvToRegister)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvSpatuan = findViewById<TextView>(R.id.tvSpatuan)

        // Toggle password visibility
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

        // Custom SPATUAN logo text
        val text = "SPATUAN"
        val spannable = SpannableString(text)
        spannable.setSpan(ForegroundColorSpan(Color.YELLOW), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.WHITE), 3, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvSpatuan.text = spannable

        // REGISTER BUTTON
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.matches(emailPattern.toRegex())) {
                Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val uid = user?.uid

                        // Simpan username ke Firestore
                        val userMap = hashMapOf(
                            "username" to username,
                            "email" to email,
                            "created_at" to FieldValue.serverTimestamp()
                        )

                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(uid!!)
                            .set(userMap)
                            .addOnSuccessListener {
                                user.sendEmailVerification()
                                    .addOnCompleteListener { verifyTask ->
                                        if (verifyTask.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Registrasi berhasil! Silakan verifikasi email Anda sebelum login.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            auth.signOut()
                                            startActivity(Intent(this, LoginActivity::class.java))
                                            finish()
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Gagal mengirim email verifikasi: ${verifyTask.exception?.message}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    this,
                                    "Gagal menyimpan data pengguna: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Registrasi gagal: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("REGISTER", "Gagal daftar", task.exception)
                    }
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