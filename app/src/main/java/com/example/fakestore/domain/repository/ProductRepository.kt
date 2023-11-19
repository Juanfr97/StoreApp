package com.example.fakestore.domain.repository

import com.example.fakestore.domain.models.Product

interface ProductRepository {
    suspend fun getProducts() : List<Product>
    suspend fun getProductById(productId: Int) : Product
}