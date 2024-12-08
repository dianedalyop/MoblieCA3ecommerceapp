package com.example.moblieca3ecommerceapp.Data.model.repository

import com.example.moblieca3ecommerceapp.Data.model.repository.Product

class ProductRepository {
    fun getProducts(): List<Product> {
        return listOf(
            Product("1", "Cute Baby Onesie", 19.99, "https://example.com/onesie.jpg"),
            Product("2", "Baby Shoes", 15.50, "https://example.com/shoes.jpg")
        )
    }
}