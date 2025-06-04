package com.uasmobilek5.spatuanapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uasmobilek5.spatuanapp.adapter.CartAdapter
import com.uasmobilek5.spatuanapp.model.CartItem

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var checkoutButton: Button
    private lateinit var homeIcon: ImageView
    private lateinit var keranjangIcon: ImageView
    private lateinit var historiIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

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

        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        checkoutButton = findViewById(R.id.checkoutButton)
        homeIcon = findViewById(R.id.home)
        keranjangIcon = findViewById(R.id.keranjang)
        historiIcon = findViewById(R.id.histori)

        val cartItems = listOf(
            CartItem(R.drawable.fast_clean, "Fast Clean", 1, 35000),
            CartItem(R.drawable.deep_clean, "Deep Clean", 2, 40000),
            CartItem(R.drawable.unyellowing, "Unyellowing", 1, 35000),
        )

        val cartAdapter = CartAdapter(cartItems)
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.adapter = cartAdapter

        checkoutButton.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        // Navigasi bawah
        homeIcon.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        keranjangIcon.setOnClickListener {
        }

        historiIcon.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            finish()
        }
    }
}