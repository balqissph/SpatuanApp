package com.uasmobilek5.spatuanapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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

        // Mengambil TextView sesuai id yang ada di layout
        val deepClean = findViewById<TextView>(R.id.deepCleanText)
        val whiteShoes = findViewById<TextView>(R.id.whiteShoesText)
        val fastClean = findViewById<TextView>(R.id.fastCleanText)
        val unyellowing = findViewById<TextView>(R.id.unyellowingText)

        deepClean.setOnClickListener {
            startActivity(Intent(this, Dashboard1Activity::class.java))
        }

        whiteShoes.setOnClickListener {
            startActivity(Intent(this, Dashboard2Activity::class.java))
        }

        fastClean.setOnClickListener {
            startActivity(Intent(this, Dashboard3Activity::class.java))
        }

        unyellowing.setOnClickListener {
            startActivity(Intent(this, Dashboard4Activity::class.java))
        }

        // Bottom Navigation Buttons
        val homeBtn = findViewById<ImageView>(R.id.home)
        val cartBtn = findViewById<ImageView>(R.id.keranjang)
        val historyBtn = findViewById<ImageView>(R.id.histori)

        homeBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        historyBtn.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}
