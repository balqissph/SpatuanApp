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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var homeIcon: ImageView
    private lateinit var keranjangIcon: ImageView
    private lateinit var historiIcon: ImageView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var databaseRef: DatabaseReference
    private val historyList = mutableListOf<HistoryItem>()

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

        historyAdapter = HistoryAdapter(historyList)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter

        // 🔥 Ambil data dari Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().getReference("history")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                historyList.clear()
                for (data in snapshot.children) {
                    val item = data.getValue(HistoryItem::class.java)
                    if (item != null) {
                        historyList.add(item)
                    }
                }
                historyAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Tangani error
            }
        })

        homeIcon.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        keranjangIcon.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
            finish()
        }

        historiIcon.setOnClickListener {
            // Sudah berada di halaman histori
        }
    }
}
