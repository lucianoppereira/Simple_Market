package com.example.simplemarket

import com.google.gson.annotations.SerializedName

class Product(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var title: String? = null,

    @SerializedName("price")
    var price: String? = null,

    @SerializedName("category")
    var category: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("photos")
    var image: String? = null


) {


}

