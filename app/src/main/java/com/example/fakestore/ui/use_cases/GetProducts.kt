package com.example.fakestore.ui.use_cases

import com.example.fakestore.domain.models.Product
import com.example.fakestore.domain.repository.ProductRepository
import com.example.fakestore.ui.states.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProducts(
    private val productRepository: ProductRepository
) {
    operator fun invoke() : Flow<ApiResult<List<Product>>> = flow {
        try{
            emit(ApiResult.Loading())
            val products = productRepository.getProducts()
            emit(ApiResult.Success(products))
        }
        catch (e: Exception) {
            emit(ApiResult.Error("Algo salio mal"))
        }
    }
}