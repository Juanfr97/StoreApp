package com.example.fakestore.data_access.source

import com.example.fakestore.domain.models.Product
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts() : List<Product>

    @GET("products/{id}")
    suspend fun getProductById(id : Int) : Product
}