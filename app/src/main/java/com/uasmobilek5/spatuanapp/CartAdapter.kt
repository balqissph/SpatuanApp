package com.uasmobilek5.spatuanapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uasmobilek5.spatuanapp.R
import com.uasmobilek5.spatuanapp.CartItem

class CartAdapter(private val items: MutableList<CartItem>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.item_image)
        val itemName: TextView = view.findViewById(R.id.item_name)
        val itemQty: TextView = view.findViewById(R.id.item_qty)
        val itemPrice: TextView = view.findViewById(R.id.item_price)
        val deleteIcon: ImageView = view.findViewById(R.id.delete_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.itemImage.setImageResource(item.imageResId)
        holder.itemName.text = item.name
        holder.itemQty.text = "Qty: ${item.qty}"
        holder.itemPrice.text = "Price: Rp. ${item.price * item.qty}"

        holder.deleteIcon.setOnClickListener {
            if (item.qty > 1) {
                item.qty--
                notifyItemChanged(position)
            } else {
                items.removeAt(position)
                com.uasmobilek5.spatuanapp.CartStorage.cartItems.remove(item)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)
            }
        }
    }


    override fun getItemCount(): Int = items.size
}
