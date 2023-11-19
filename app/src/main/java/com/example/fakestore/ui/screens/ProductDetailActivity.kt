package com.example.fakestore.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.fakestore.FakeStoreApp
import com.example.fakestore.R
import com.example.fakestore.helpers.viewModelFactory
import com.example.fakestore.ui.viewmodels.ProductDetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var productNameTV : TextView
    private lateinit var productPriceTV : TextView
    private lateinit var productDescriptionTV : TextView
    private lateinit var productCategoryTV : TextView
    private lateinit var productImage : ImageView
    private lateinit var productRating : TextView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        productNameTV = findViewById(R.id.productName)
        productPriceTV = findViewById(R.id.productPrice)
        productDescriptionTV = findViewById(R.id.productDescription)
        productCategoryTV = findViewById(R.id.productCategory)
        productImage = findViewById(R.id.productImage)
        progressBar = findViewById(R.id.progressBarProductDetail)
        productRating = findViewById(R.id.product_detail_rating)

        val productId = intent.getIntExtra("productId", 0)
        val productDetailViewModel : ProductDetailViewModel by viewModels{
            viewModelFactory {
                ProductDetailViewModel(
                    productId,
                    FakeStoreApp.appModule.getProductUseCase
                )
            }
        }
        val state = productDetailViewModel.productState
        lifecycleScope.launchWhenStarted {
            state.collect{
                val product = state.value.product
                if(it.isLoading){
                    progressBar.visibility = ProgressBar.VISIBLE
                    productNameTV.visibility = TextView.GONE
                    productPriceTV.visibility = TextView.GONE
                    productDescriptionTV.visibility = TextView.GONE
                    productCategoryTV.visibility = TextView.GONE
                    productImage.visibility = ImageView.GONE
                    productRating.visibility = TextView.GONE
                }
                else{
                    progressBar.visibility = ProgressBar.GONE
                    productNameTV.visibility = TextView.VISIBLE
                    productPriceTV.visibility = TextView.VISIBLE
                    productDescriptionTV.visibility = TextView.VISIBLE
                    productCategoryTV.visibility = TextView.VISIBLE
                    productImage.visibility = ImageView.VISIBLE
                    productRating.visibility = TextView.VISIBLE

                    productNameTV.text = product?.title
                    productPriceTV.text = product?.computedPrice.toString()
                    productDescriptionTV.text = product?.description
                    productCategoryTV.text = product?.category
                    productRating.text = product?.rating?.rate.toString()
                    Picasso.get().load(product?.image).into(productImage)
                }

                if(it.error.isNotEmpty()){
                    progressBar.visibility = ProgressBar.GONE
                    productNameTV.visibility = TextView.GONE
                    productPriceTV.visibility = TextView.GONE
                    productDescriptionTV.visibility = TextView.GONE
                    productCategoryTV.visibility = TextView.GONE
                    productImage.visibility = ImageView.GONE
                    Snackbar.make(this@ProductDetailActivity,productNameTV,it.error, Snackbar.LENGTH_LONG).show()
                }
            }
    }
}
}