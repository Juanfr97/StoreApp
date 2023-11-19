package com.example.fakestore.ui.states

import com.example.fakestore.domain.models.Product

data class ProductState(
    val product : Product? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)