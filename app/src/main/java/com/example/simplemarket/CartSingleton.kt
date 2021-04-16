package com.example.simplemarket


import android.content.Context
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xwray.groupie.GroupieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import java.math.BigDecimal
import java.math.RoundingMode

object CartSingleton {

    val categories = ArrayList<String>()

    fun fetchContent(service: ApiService, adapter: GroupieAdapter) {

        service.getAllProducts().enqueue(object : Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                val productList = response.body()
                if (productList != null) {
                    for (item in productList) {

                        adapter.add(ProductListItem(item))

                    }
                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }


    fun fetchContentByCategory(service: ApiService, adapter: GroupieAdapter, category: String) {

        service.getAllProducts().enqueue(object : Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                val productList = response.body()

                if (productList != null) {
                    for (item in productList) {

                        if (item.category.equals(category, true)) {

                            adapter.add(ProductListItem(item))

                        }
                    }
                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }

    fun getCategories(service: ApiService): ArrayList<String> {

        service.getAllProducts().enqueue(object : Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                val productList = response.body()

                if (productList != null) {
                    for (item in productList) {
                        if (!categories.contains("Todos los productos"))
                            categories.add("Todos los productos")

                        if (!categories.contains(firstUpperCase(item.category.toString())))
                            categories.add(firstUpperCase(item.category.toString()))

                    }
                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }

        })

        return categories

    }

    fun priceFormat(input: String): String {

        val result = input.toDouble() * 98.25 * 1.64

        return BigDecimal(result).setScale(2, RoundingMode.CEILING).toString()

    }

    fun firstUpperCase(str: String): String {
        return str.substring(0, 1).toUpperCase() + str.substring(1)
    }




}