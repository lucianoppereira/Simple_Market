package com.example.simplemarket

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simplemarket.databinding.FragmentListBinding
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemClickListener


class ListFragment : Fragment() {

    lateinit var service: ApiService
    lateinit var adapter: GroupieAdapter
    lateinit var spinnerAdapter: SpinnerAdapter
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


        val categoryList = CartSingleton.getCategories(service)
        binding.spinner.adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            categoryList
        )

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                adapter.clear()
                CartSingleton.fetchContentByCategory(
                    service,
                    adapter,
                    binding.spinner.selectedItem.toString()
                )
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.spinner.setSelection(0)
                cartSingleton.fetchContent(service, adapter)
                adapter.notifyDataSetChanged()
            }
        }


        val toolbar = view.findViewById<Toolbar>(R.id.list_fragmebt_toolbar)

        toolbar.inflateMenu(R.menu.menu_list_fragment)

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.go_to_cart) {

                findNavController().navigate(R.id.action_listFragment_to_cartFragment)

            }
            true
        }

    }



    override fun onResume() {
        super.onResume()

        cartSingleton.fetchContent(service, adapter)
    }

}


