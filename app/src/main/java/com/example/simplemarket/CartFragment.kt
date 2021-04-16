package com.example.simplemarket


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.simplemarket.databinding.FragmentCartBinding
import com.xwray.groupie.GroupieAdapter
import java.math.BigDecimal
import java.math.RoundingMode


class CartFragment : Fragment(), ItemCallback {

    lateinit var binding: FragmentCartBinding
    lateinit var adapter: GroupieAdapter
    lateinit var textView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = FragmentCartBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GroupieAdapter()
        fetchCart(MarketCart.getCart(), adapter)

        binding.keepBuying.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_listFragment)
        }

        binding.cartEmpty.setOnClickListener {
            MarketCart.removeAllItems(requireContext())
            calcTotals()
            onCartClear()
        }

        binding.recyclerCart.adapter = adapter

        var totalPrice = MarketCart.getCart()
            .fold(0.toDouble()) { acc, cartItem ->
                acc + cartItem.quantity.times(cartItem.product.price!!.toDouble())
            }


        textView = binding.totalPrice
        calcTotals()




    }

    fun fetchCart(cartItemList: List<CartItem>, adapter: GroupieAdapter) {

        for (item in cartItemList) {
            adapter.add(ProductCartItem(item, this))
        }

    }


    override fun onItemRemove(position: ProductCartItem) {
        if (position.cartItem.quantity < 1)
            adapter.remove(position)
        calcTotals()

    }

    override fun calcTotals() {
        var totalPrice = MarketCart.getCart()
            .fold(0.toDouble()) { acc, cartItem ->
                acc + cartItem.quantity.times(cartItem.product.price!!.toDouble())
            }

        textView.text = "$${CartSingleton.priceFormat(totalPrice.toString())}"

//        return "$${CartSingleton.priceFormat(totalPrice.toString())}"
    }

    override fun onCartClear() {
        Toast.makeText(requireContext(), R.string.remove_from_Cart, Toast.LENGTH_SHORT).show()
        adapter.clear()
    }

}

interface ItemCallback {
    fun onItemRemove(position: ProductCartItem)
    fun onCartClear()
    fun calcTotals()

}