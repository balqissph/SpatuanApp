package com.uasmobilek5.spatuanapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val itemList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val qty: TextView = itemView.findViewById(R.id.qty)
        val price: TextView = itemView.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = itemList[position]
        holder.image.setImageResource(item.imageResId)
        holder.name.text = item.name
        holder.qty.text = "Qty: ${item.qty}"
        holder.price.text = "Price: Rp. ${item.price}"
    }

    override fun getItemCount(): Int = itemList.size
}
