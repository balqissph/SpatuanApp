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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var homeIcon: ImageView
    private lateinit var keranjangIcon: ImageView
    private lateinit var historiIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val tvSpatuan = findViewById<TextView>(R.id.tvSpatuan)
        val text = "SPATUAN"
        val spannable = SpannableString(text)
        spannable.setSpan(ForegroundColorSpan(Color.YELLOW), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.WHITE), 3, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvSpatuan.text = spannable

        historyRecyclerView = findViewById(R.id.historyRecyclerView)
        homeIcon = findViewById(R.id.home)
        keranjangIcon = findViewById(R.id.keranjang)
        historiIcon = findViewById(R.id.histori)

        // Dummy data
        val historyList = listOf(
            HistoryItem(R.drawable.fast_clean, "Fast Clean", 3, 90000),
            HistoryItem(R.drawable.deep_clean, "Deep Clean", 2, 80000),
            HistoryItem(R.drawable.unyellowing, "Unyellowing", 1, 35000)
        )

        val adapter = HistoryAdapter(historyList)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = adapter

        homeIcon.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        keranjangIcon.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
            finish()
        }

        historiIcon.setOnClickListener {
        }
    }
}