package com.example.fakestore.data_access.source

import com.example.fakestore.domain.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    suspend fun getProducts() : List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id : Int) : Product
}