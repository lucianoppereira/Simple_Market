package com.example.simplemarket


import com.xwray.groupie.GroupieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

                        if (item.category.equals(category, true)){

                            adapter.add(ProductListItem(item))

                        }else if (category.equals("Todos los productos")){
                            fetchContent(service, adapter)
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

    fun firstUpperCase(str: String): String {
        return str.substring(0, 1).toUpperCase() + str.substring(1)
    }

}