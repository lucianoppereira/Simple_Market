package com.example.simplemarket

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemarket.databinding.FragmentCartBinding
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var adapter: GroupieAdapter
    val section = Section()


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



        binding.keepBuying.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_listFragment)
        }

        binding.cartEmpty.setOnClickListener {
            MarketCart.removeAllItems(requireContext())
        }

        binding.recyclerCart.adapter = adapter

        var totalPrice = MarketCart.getCart()
            .fold(0.toDouble()) { acc, cartItem ->
                acc + cartItem.quantity.times(cartItem.product.price!!.toDouble())
            }

        binding.totalPrice.text = "$${totalPrice}"

    }


    override fun onResume() {
        super.onResume()

        fetchCart(MarketCart.getCart(), adapter)

    }

    companion object {

        fun fetchCart(cartItemList: List<CartItem>, adapter: GroupieAdapter) {

            for (item in cartItemList) {

                adapter.add(ProductCartItem(item))

            }

        }
    }

}