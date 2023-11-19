package com.example.fakestore.ui.events

sealed class ProductsEvent {
    data class OnSearch(val query: String) : ProductsEvent()
    data object OnInit : ProductsEvent()
}