package com.uasmobilek5.spatuanapp

import android.app.AlertDialog
import android.content.Context
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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val eyeClosed = findViewById<ImageView>(R.id.eye_closed)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvToRegister = findViewById<TextView>(R.id.tvToRegister)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val tvSpatuan = findViewById<TextView>(R.id.tvSpatuan)
        val cbRememberMe = findViewById<CheckBox>(R.id.checkboxRemember)

        // SharedPreferences
        val sharedPref = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Load saved credentials
        val savedEmail = sharedPref.getString("email", "")
        val savedPassword = sharedPref.getString("password", "")
        val remember = sharedPref.getBoolean("remember", false)

        if (remember) {
            etEmail.setText(savedEmail)
            etPassword.setText(savedPassword)
            cbRememberMe.isChecked = true
        }

        // Toggle password visibility
        eyeClosed.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                eyeClosed.setImageResource(R.drawable.eye_open)
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                eyeClosed.setImageResource(R.drawable.eye_closed)
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // SPATUAN text color
        val text = "SPATUAN"
        val spannable = SpannableString(text)
        spannable.setSpan(ForegroundColorSpan(Color.YELLOW), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.WHITE), 3, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvSpatuan.text = spannable

        // Login button
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (!email.matches(emailPattern.toRegex())) {
                Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save credentials if Remember Me is checked
            if (cbRememberMe.isChecked) {
                sharedPref.edit().apply {
                    putString("email", email)
                    putString("password", password)
                    putBoolean("remember", true)
                    apply()
                }
            } else {
                sharedPref.edit().clear().apply()
            }

            loginUser(email, password)
        }

        // Back button
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // Go to Register
        tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Forgot password
        tvForgotPassword.setOnClickListener {
            val emailEditText = EditText(this)
            emailEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            emailEditText.hint = "Masukkan email anda"

            val dialog = AlertDialog.Builder(this)
                .setTitle("Reset Password")
                .setMessage("Kami akan mengirimkan link reset ke email anda.")
                .setView(emailEditText)
                .setPositiveButton("Kirim") { _, _ ->
                    val email = emailEditText.text.toString().trim()
                    if (email.isEmpty()) {
                        Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Link reset dikirim ke $email", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this, "Gagal mengirim email reset: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                .setNegativeButton("Batal", null)
                .create()

            dialog.show()
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        val uid = user.uid
                        val userEmail = user.email ?: ""

                        val db = FirebaseFirestore.getInstance()
                        val userDocRef = db.collection("users").document(uid)

                        userDocRef.get().addOnSuccessListener { document ->
                            if (!document.exists()) {
                                val userData = hashMapOf(
                                    "email" to userEmail,
                                    "createdAt" to FieldValue.serverTimestamp()
                                )
                                userDocRef.set(userData)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Data user berhasil disimpan", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, HomeActivity::class.java))
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Gagal menyimpan data user: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Gagal cek data user: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Silakan verifikasi email Anda terlebih dahulu", Toast.LENGTH_LONG).show()
                        auth.signOut()
                    }
                } else {
                    Toast.makeText(this, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LOGIN", "Login error", task.exception)
                }
            }
    }
}
