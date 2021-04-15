package com.example.simplemarket

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("products")
    fun getAllProducts(): Call<List<Product>>

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>

    @GET("products/{category}")
    fun getProductByCategory(@Path("category") category: String): Call<Product>

}