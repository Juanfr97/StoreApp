package com.example.fakestore.domain.models

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String,
    val rating: Rating
){
    val computedPrice get() = "$$price"

    //Si el text del titulo es muy largo, cortarlo y colocar puntos suspensivos
    val computedTitle get() = if(title.length > 10) title.substring(0,10) + "..." else title
}