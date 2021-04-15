package com.example.simplemarket

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitInstance {

    companion object{

        val baseUrl = "https://fakestoreapi.com/"

        fun getRetrofitInstance(): Retrofit{

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

        }

    }

}