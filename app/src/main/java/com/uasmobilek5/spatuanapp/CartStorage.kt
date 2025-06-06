package com.uasmobilek5.spatuanapp

import com.uasmobilek5.spatuanapp.CartItem

object CartStorage {
    val cartItems = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        val existingItem = cartItems.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.qty += item.qty
        } else {
            cartItems.add(item)
        }
    }

    fun removeItem(item: CartItem) {
        cartItems.remove(item)
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun totalPrice(): Int {
        return cartItems.sumOf { it.price * it.qty }
    }
}
