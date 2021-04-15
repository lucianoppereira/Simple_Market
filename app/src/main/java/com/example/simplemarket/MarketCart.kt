package com.example.simplemarket

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import io.paperdb.Paper

class MarketCart {

    companion object {
        fun addItem(cartItem: CartItem, context: Context) {
            val cart = MarketCart.getCart()

            val targetItem = cart.singleOrNull { it.product.id == cartItem.product.id }
            if (targetItem == null) {
                cartItem.quantity++
                cart.add(cartItem)
                Toast.makeText(context, R.string.add_to_Cart, Toast.LENGTH_SHORT).show()
            } else {
                targetItem.quantity++
            }
            MarketCart.saveCart(cart)
        }

        fun removeAllItems(context: Context) {

            val cart = getCart()
            cart.clear()
            Toast.makeText(context, R.string.empty_cart, Toast.LENGTH_SHORT).show()

            MarketCart.saveCart(cart)


        }

        fun directRemoveItem(cartItem: CartItem, context: Context) {

            val cart = MarketCart.getCart()
            val targetItem = cart.singleOrNull { it.product.id == cartItem.product.id }

            if (targetItem != null) {
                targetItem.quantity = 0
                cart.remove(targetItem)
                Toast.makeText(context, R.string.remove_from_Cart, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, R.string.remove_from_Cart, Toast.LENGTH_SHORT).show()
            }

            MarketCart.saveCart(cart)

        }

        fun removeItem(cartItem: CartItem, context: Context) {

            val cart = MarketCart.getCart()
            val targetItem = cart.singleOrNull { it.product.id == cartItem.product.id }

            if (targetItem != null) {
                if (targetItem.quantity > 0) {
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                    Toast.makeText(context, R.string.remove_from_Cart, Toast.LENGTH_SHORT).show()
                }
            }

            MarketCart.saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {

            return Paper.book().read("cart", mutableListOf())
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun getShoppingCartSize(): Int {
            var cartSize = 0
            MarketCart.getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }

}