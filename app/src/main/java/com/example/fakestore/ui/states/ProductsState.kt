package com.example.fakestore.ui.states

import com.example.fakestore.domain.models.Product

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<Product>? = emptyList(),
    val error: String = ""
)