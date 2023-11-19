package com.example.fakestore.ui.use_cases

import com.example.fakestore.domain.models.Product
import com.example.fakestore.domain.repository.ProductRepository
import com.example.fakestore.ui.states.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProduct(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId : Int) : Flow<ApiResult<Product>> = flow {
        emit(ApiResult.Loading())
        try {
            val product = productRepository.getProductById(productId)
            emit(ApiResult.Success(product))
        } catch (e: Exception) {
            emit(ApiResult.Error("Algo salió mal"))
        }
    }
}