package com.uasmobilek5.spatuanapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.uasmobilek5.spatuanapp.model.CartItem

class Dashboard1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard1)

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

        // tombol back
        val backButton = findViewById<ImageView>(R.id.myIcon)
        backButton.setOnClickListener {
            finish()
        }

        // tombol add
        val addButton = findViewById<AppCompatButton>(R.id.button)
        addButton.setOnClickListener {
            val newItem = CartItem(R.drawable.deep_clean, "Deep Clean", 1, 35000)

            val existingItem = CartStorage.cartItems.find { it.name == newItem.name }

            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)

                CartStorage.cartItems.remove(existingItem)
                CartStorage.cartItems.add(updatedItem)
            } else {
                CartStorage.cartItems.add(newItem)
            }
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
}
