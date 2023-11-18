package com.example.fakestore.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.fakestore.FakeStoreApp
import com.example.fakestore.R
import com.example.fakestore.helpers.viewModelFactory
import com.example.fakestore.ui.viewmodels.ProductsViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val productsViewModel : ProductsViewModel by viewModels{
            viewModelFactory {
                ProductsViewModel(
                    FakeStoreApp.appModule.getProductsUseCase
                )
            }
        }
        val state = productsViewModel.productsState
        lifecycleScope.launchWhenStarted {
            state.collect { state ->
                Log.i("TEST",state.products.toString())
            }
        }
    }
}