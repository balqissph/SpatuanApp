package com.uasmobilek5.spatuanapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

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

        val etNama = findViewById<EditText>(R.id.etNama)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val checkboxDana = findViewById<CheckBox>(R.id.checkbox_dana)
        val checkboxShopee = findViewById<CheckBox>(R.id.checkbox_shopee)
        val checkboxCOD = findViewById<CheckBox>(R.id.checkbox_cod)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // Tambahkan ini untuk navigasi bawah
        val homeIcon = findViewById<ImageView>(R.id.home)
        val keranjangIcon = findViewById<ImageView>(R.id.keranjang)
        val historiIcon = findViewById<ImageView>(R.id.histori)

        val paymentCheckboxes = listOf(checkboxDana, checkboxShopee, checkboxCOD)

        paymentCheckboxes.forEach { checkbox ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    paymentCheckboxes.filter { it != checkbox }.forEach {
                        it.isChecked = false
                    }
                }
            }
        }

        btnSubmit.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val selectedPayment = when {
                checkboxDana.isChecked -> "Dana"
                checkboxShopee.isChecked -> "Shopeepay"
                checkboxCOD.isChecked -> "COD"
                else -> ""
            }

            if (nama.isEmpty()) {
                etNama.error = "Nama harus diisi"
                etNama.requestFocus()
                return@setOnClickListener
            }

            if (alamat.isEmpty()) {
                etAlamat.error = "Alamat harus diisi"
                etAlamat.requestFocus()
                return@setOnClickListener
            }

            if (selectedPayment.isEmpty()) {
                Toast.makeText(this, "Pilih metode pembayaran", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val message = "Nama: $nama\nAlamat: $alamat\nMetode Pembayaran: $selectedPayment"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        // Navigasi bawah
        homeIcon.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        keranjangIcon.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
            finish()
        }

        historiIcon.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            finish()
        }
    }
}