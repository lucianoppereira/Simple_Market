package com.example.simplemarket

import com.google.gson.annotations.SerializedName

class Product(
//    var id : Int= 0,
//    var title : String= "",
//    var price : String= "",
//    var category : String="",
//    var description : String="",
//    var image : String=""

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

