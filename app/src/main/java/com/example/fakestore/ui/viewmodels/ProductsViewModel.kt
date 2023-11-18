package com.example.fakestore.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.ui.events.ProductsEvent
import com.example.fakestore.ui.states.ApiResult
import com.example.fakestore.ui.states.ProductsState
import com.example.fakestore.ui.use_cases.GetProducts
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductsViewModel(
    private val getProductsUseCase: GetProducts
) :  ViewModel() {
    private var productsJob : Job? = null
    private var _productsState = MutableStateFlow(ProductsState())
    val productsState = _productsState.asStateFlow()
    init {
       getProducts()
    }

    fun onEvent(event: ProductsEvent) {
        when(event) {
            is ProductsEvent.OnSearch -> {
                val query = event.query
                val filteredList = productsState.value.products?.filter {
                    it.title.contains(query, true)
                }
                _productsState.value = _productsState.value.copy(products = filteredList)
            }
        }
    }

    private fun getProducts() {
        productsJob?.cancel()
        productsJob = getProductsUseCase().onEach {result ->
            when(result) {
                is ApiResult.Loading -> {
                    _productsState.value = ProductsState(isLoading = true)
                }
                is ApiResult.Success -> {
                    _productsState.value = ProductsState(products = result.data)
                }
                is ApiResult.Error -> {
                    _productsState.value = ProductsState(error = result.message ?: "Ocurrió un error")
                }
            }
        }.launchIn(viewModelScope)
    }
}