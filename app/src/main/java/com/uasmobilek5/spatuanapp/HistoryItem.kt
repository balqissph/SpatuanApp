package com.uasmobilek5.spatuanapp

import com.uasmobilek5.spatuanapp.OrderItem

data class HistoryItem(
    val nama: String = "",
    val alamat: String = "",
    val metodePembayaran: String = "",
    val items: List<OrderItem> = emptyList(),
    val totalHarga: Int = 0,
    val timestamp: Long = 0
)


