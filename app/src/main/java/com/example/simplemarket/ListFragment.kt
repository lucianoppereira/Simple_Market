package com.example.simplemarket

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simplemarket.databinding.FragmentListBinding
import com.xwray.groupie.GroupieAdapter



class ListFragment : Fragment() {

    lateinit var service: ApiService
    lateinit var adapter: GroupieAdapter
    lateinit var binding: FragmentListBinding
    private val cartSingleton: CartSingleton = CartSingleton
    lateinit var category: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        service = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GroupieAdapter()
        binding.recyclerContent.adapter = adapter

        binding.spinner.adapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            options
        )

        val toolbar = view.findViewById<Toolbar>(R.id.list_fragmebt_toolbar)

        toolbar.inflateMenu(R.menu.menu_list_fragment)
//        val searchItem = view.findViewById<SearchView>(R.id.search_product)

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.go_to_cart) {

                findNavController().navigate(R.id.action_listFragment_to_cartFragment)

            } else if (it.itemId == R.id.search_product) {

//                searchItem.onActionViewExpanded()
            }
            true
        }

    }

    override fun onResume() {
        super.onResume()
        cartSingleton.fetchContent(service, adapter)
    }

}