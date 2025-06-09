package com.uasmobilek5.spatuanapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderActivity : AppCompatActivity() {

    private lateinit var tvTotalPesanan: TextView
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val tvSpatuan = findViewById<TextView>(R.id.tvSpatuan)
        val text = "SPATUAN"
        val spannable = SpannableString(text)
        spannable.setSpan(ForegroundColorSpan(Color.YELLOW), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.WHITE), 3, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvSpatuan.text = spannable

        val etNama = findViewById<EditText>(R.id.etNama)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val checkboxDana = findViewById<CheckBox>(R.id.checkbox_dana)
        val checkboxShopee = findViewById<CheckBox>(R.id.checkbox_shopee)
        val checkboxCOD = findViewById<CheckBox>(R.id.checkbox_cod)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        tvTotalPesanan = findViewById(R.id.tvTotalPesanan)

        val homeIcon = findViewById<ImageView>(R.id.home)
        val keranjangIcon = findViewById<ImageView>(R.id.keranjang)
        val historiIcon = findViewById<ImageView>(R.id.histori)

        val paymentCheckboxes = listOf(checkboxDana, checkboxShopee, checkboxCOD)
        paymentCheckboxes.forEach { checkbox ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    paymentCheckboxes.filter { it != checkbox }.forEach { it.isChecked = false }
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

            val cartItems = CartStorage.cartItems
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Keranjang kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val orderItems = cartItems.map {
                OrderItem(it.name, it.qty, it.price)
            }

            val totalHarga = cartItems.sumOf { it.qty * it.price }

            val order = Order(
                nama = nama,
                alamat = alamat,
                metodePembayaran = selectedPayment,
                items = orderItems,
                totalHarga = totalHarga,
                timestamp = System.currentTimeMillis()
            )

            val userId = auth.currentUser?.uid
            if (userId == null) {
                Toast.makeText(this, "User belum login", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            db.collection("users")
                .document(userId)
                .collection("histori")
                .add(order)
                .addOnSuccessListener {
                    Toast.makeText(this, "Pesanan berhasil disimpan", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, HistoryActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal menyimpan pesanan: ${e.message}", Toast.LENGTH_LONG).show()
                }
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

        tampilkanTotalPesanan()
    }

    private fun tampilkanTotalPesanan() {
        val cartItems = CartStorage.cartItems

        if (cartItems.isEmpty()) {
            tvTotalPesanan.text = "Keranjang kosong"
            return
        }

        val detail = StringBuilder()
        var totalHarga = 0

        for (item in cartItems) {
            val subtotal = item.qty * item.price
            detail.append("• ${item.name} ( Qty : ${item.qty} ) : Rp $subtotal\n")
            totalHarga += subtotal
        }

        detail.append("Jumlah : Rp $totalHarga")
        tvTotalPesanan.text = detail.toString()
    }
}
