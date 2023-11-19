package com.example.fakestore.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.ui.states.ApiResult
import com.example.fakestore.ui.states.ProductState
import com.example.fakestore.ui.use_cases.GetProduct
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: Int,
    private val getProductUseCasse : GetProduct
) : ViewModel() {

    private val _productState = MutableStateFlow(ProductState())
    val productState = _productState.asStateFlow()

    private var getProductJob : Job? = null
    init {
        getProduct()
    }

    private fun getProduct(){
        getProductJob?.cancel()
        getProductJob = getProductUseCasse(productId).onEach { result ->
            when(result) {
                is ApiResult.Loading -> {
                    _productState.value = ProductState(isLoading = true)
                }
                is ApiResult.Success -> {
                    _productState.value = ProductState(product = result.data, isLoading = false)
                }
                is ApiResult.Error -> {
                    _productState.value = ProductState(error = result.message ?: "Ocurrió un error")
                }
            }
        }.launchIn(viewModelScope)
    }
}