package com.example.fakestore.domain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.R
import com.example.fakestore.domain.models.Product
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private val products: List<Product>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    class ProductsViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val categoryTV : TextView
        val priceTV : TextView
        val imageIV : ImageView
        val titleTV : TextView
        val ratingTV : TextView

        init {
            categoryTV = itemView.findViewById<TextView>(R.id.product_category)
            priceTV = itemView.findViewById<TextView>(R.id.product_price)
            imageIV = itemView.findViewById<ImageView>(R.id.product_image)
            titleTV = itemView.findViewById<TextView>(R.id.product_title)
            ratingTV = itemView.findViewById<TextView>(R.id.product_rating)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        view.setOnClickListener {
            val position = it.tag as Int
            listener.onItemClick(products[position])
        }
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.categoryTV.text = product.category
        holder.priceTV.text = product.price.toString()
        holder.titleTV.text = product.title
        holder.ratingTV.text = product.rating.rate.toString()
        Picasso.get().load(product.image).into(holder.imageIV)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}