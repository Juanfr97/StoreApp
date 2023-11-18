package com.example.fakestore.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.FakeStoreApp
import com.example.fakestore.R
import com.example.fakestore.domain.adapters.ProductsAdapter
import com.example.fakestore.domain.models.Product
import com.example.fakestore.helpers.viewModelFactory
import com.example.fakestore.ui.viewmodels.ProductsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var recyclerView: RecyclerView
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
        recyclerView = findViewById(R.id.recyclerProducts)
        lifecycleScope.launchWhenStarted {
            state.collect { state ->
                val products = state.products
                setupRecyclerView(products!!)
                Log.i("TEST",state.products.toString())
            }
        }
    }

    fun setupRecyclerView(products : List<Product>){
        productsAdapter = ProductsAdapter(products, object : ProductsAdapter.OnItemClickListener {
            override fun onItemClick(product: Product) {
                Log.i("TEST",product.toString())
            }
        })
        val layoutManager = GridLayoutManager(this@MainActivity, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = productsAdapter

    }
}