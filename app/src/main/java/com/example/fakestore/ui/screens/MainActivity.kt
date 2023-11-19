package com.example.fakestore.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.FakeStoreApp
import com.example.fakestore.R
import com.example.fakestore.domain.adapters.ProductsAdapter
import com.example.fakestore.domain.models.Product
import com.example.fakestore.helpers.SpacesItemDecoration
import com.example.fakestore.helpers.viewModelFactory
import com.example.fakestore.ui.events.ProductsEvent
import com.example.fakestore.ui.viewmodels.ProductsViewModel
import com.google.android.material.search.SearchBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar : ProgressBar
    private lateinit var searchView: androidx.appcompat.widget.SearchView
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
        progressBar = findViewById(R.id.progressBar)
        searchView = findViewById(R.id.searchView)


        lifecycleScope.launchWhenStarted {
            state.collect { state ->
                val products = state.products
                setupRecyclerView(products!!)
                if(state.isLoading){
                    progressBar.visibility = ProgressBar.VISIBLE
                    recyclerView.visibility = RecyclerView.GONE
                }
                else{
                    progressBar.visibility = ProgressBar.GONE
                    recyclerView.visibility = RecyclerView.VISIBLE
                }
                if(state.error.isNotEmpty()){
                    Snackbar.make(this@MainActivity,recyclerView,state.error,Snackbar.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun setupRecyclerView(products : List<Product>){
        productsAdapter = ProductsAdapter(products.toMutableList(), object : ProductsAdapter.OnItemClickListener {
            override fun onItemClick(product: Product) {
                val intent = Intent(this@MainActivity, ProductDetailActivity::class.java).also {
                    it.putExtra("productId",product.id)
                }
                startActivity(intent)
            }
        })
        val spacing = 8
        recyclerView.addItemDecoration(SpacesItemDecoration(spacing))
        val layoutManager = GridLayoutManager(this@MainActivity, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = productsAdapter

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                productsAdapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productsAdapter.getFilter().filter(newText)
                return false
            }

        })

    }
}