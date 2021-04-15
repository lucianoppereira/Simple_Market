package com.example.simplemarket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xwray.groupie.GroupieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductListViewModel() : ViewModel() {

    private var productList: MutableLiveData<List<Product>> = MutableLiveData()
    lateinit var productListItem: ProductListItem
    lateinit var service: ApiService

    fun getList(): LiveData<List<Product>> {
        return productList
    }

    fun setAdapterData(adapter: GroupieAdapter) {


        for (item in (productList as List<Product>)) {

            adapter.add(ProductListItem(item))

        }
    }

    fun apiServiceCall(service: Call<List<Product>>) {

//        val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)

        service.enqueue(object : Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                if (response.isSuccessful)
                    productList.postValue(response.body())
                else (
                        productList.postValue(null)
                        )
//                if (productList != null) {
//                    for (item in productList) {
//
//                        adapter.add(ProductListItem(item))
//
//                    }
//                }


            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }


}

