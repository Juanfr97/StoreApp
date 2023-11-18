package com.example.fakestore.data_access.repositories

import com.example.fakestore.data_access.source.ProductService
import com.example.fakestore.domain.models.Product
import com.example.fakestore.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productService: ProductService
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }
}