package com.uasmobilek5.spatuanapp

import com.uasmobilek5.spatuanapp.OrderItem

data class Order(
    var nama: String? = null,
    var alamat: String? = null,
    var metodePembayaran: String? = null,
    var items: List<OrderItem> = listOf(),
    var totalHarga: Int = 0,
    var timestamp: Long = 0L
)

