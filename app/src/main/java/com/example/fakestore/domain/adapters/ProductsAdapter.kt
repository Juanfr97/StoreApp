package com.example.fakestore.domain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.R
import com.example.fakestore.domain.models.Product
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private val products: MutableList<Product>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(), Filterable {
    private val productsFull = products.toMutableList()
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
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.categoryTV.text = product.category
        holder.priceTV.text = product.computedPrice
        holder.titleTV.text = product.computedTitle
        holder.ratingTV.text = product.rating.rate.toString()
        Picasso.get().load(product.image).resize(600, 200)
            .centerInside() .into(holder.imageIV)

        holder.itemView.setOnClickListener {
            listener.onItemClick(product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Product>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(products)
            } else {
                val filterPattern = constraint.toString().lowercase().trim()
                for (item in productsFull) {
                    if (item.title.lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            products.clear()
            products.addAll(results?.values as List<Product>)
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
       return filter
    }
}