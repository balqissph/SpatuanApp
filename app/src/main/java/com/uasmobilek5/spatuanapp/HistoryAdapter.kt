package com.uasmobilek5.spatuanapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val orderList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama = itemView.findViewById<TextView>(R.id.tvNama)
        val tvAlamat = itemView.findViewById<TextView>(R.id.tvAlamat)
        val tvMetode = itemView.findViewById<TextView>(R.id.tvMetode)
        val tvItems = itemView.findViewById<TextView>(R.id.tvItems)
        val tvTotal = itemView.findViewById<TextView>(R.id.tvTotal)
        val tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)

        val imageOrder = itemView.findViewById<ImageView>(R.id.imageOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val order = orderList[position]

        holder.tvNama.text = "Nama: ${order.nama}"
        holder.tvAlamat.text = "Alamat: ${order.alamat}"
        holder.tvMetode.text = "Pembayaran: ${order.metodePembayaran}"
        holder.tvTotal.text = "Total: Rp ${order.totalHarga}"
        holder.tvTanggal.text = formatTanggal(order.timestamp)

        val itemListString = order.items.joinToString("\n") {
            "- ${it.name} (Qty: ${it.qty}, Rp ${it.price})"
        }
        holder.tvItems.text = itemListString


        val itemName = order.items.firstOrNull()?.name?.lowercase() ?: ""

        when {
            "unyellowing" in itemName -> holder.imageOrder.setImageResource(R.drawable.unyellowing)
            "fast clean" in itemName -> holder.imageOrder.setImageResource(R.drawable.fast_clean)
            "white shoes treatment" in itemName -> holder.imageOrder.setImageResource(R.drawable.white_shoes)
            else -> holder.imageOrder.setImageResource(R.drawable.deep_clean)
        }
    }


    override fun getItemCount(): Int = orderList.size

    private fun formatTanggal(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        return "Tanggal: ${sdf.format(Date(timestamp))}"
    }
}
