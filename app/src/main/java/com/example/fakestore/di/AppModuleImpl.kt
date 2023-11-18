package com.example.fakestore.di

import android.content.Context
import com.example.fakestore.data_access.repositories.ProductRepositoryImpl
import com.example.fakestore.data_access.source.ProductService
import com.example.fakestore.domain.repository.ProductRepository
import com.example.fakestore.ui.use_cases.GetProducts
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


interface AppModule {
    val productService : ProductService
    val productRepository : ProductRepository
    val getProductsUseCase : GetProducts
}

class AppModuleImpl(
    private val context: Context
) : AppModule {
    private val BASE_URL ="https://fakestoreapi.com/"
    override val productService: ProductService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .build()
            )
            .build()
            .create(ProductService::class.java)
    }
    override val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(productService)
    }
    override val getProductsUseCase: GetProducts by lazy {
        GetProducts(productRepository)
    }
}