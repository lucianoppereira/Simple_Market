package com.example.simplemarket


import com.xwray.groupie.GroupieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CartSingleton {

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




}