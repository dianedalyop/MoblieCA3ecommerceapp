package com.example.moblieca3ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.moblieca3ecommerceapp.ui.screens.HomeScreen
import com.example.moblieca3ecommerceapp.ui.theme.MoblieCA3ecommerceappTheme
import coil.compose.rememberImagePainter
import com.example.moblieca3ecommerceapp.data.model.Product

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MoblieCA3ecommerceappTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController = navController) }
                    composable("shop") { ShopScreen() } // Define your Shop screen here
                }
            }
        }
    }
}

@Composable
fun ShopScreen() {
    // Sample product list
    val products = listOf(
        Product("Baby Onesie", "Pink", "M", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz2Mx3a8lJKVDR8gIL2sLhnjVhYKsKPanirw&s"),
        Product("Baby T-Shirt", "Blue", "S", "https://example.com/tshirt.jpg"),
        Product("Baby Pants", "Green", "L", "https://example.com/pants.jpg"),
        Product("Baby Jacket", "Red", "XL", "https://example.com/jacket.jpg"),
        Product("Baby Hat", "Yellow", "M", "https://example.com/hat.jpg")
    )


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(products) { product ->
                ProductCard(product)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically


        ) {
            // images
            Image(
                painter = rememberImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Color: ${product.color}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Size: ${product.size}", style = MaterialTheme.typography.bodyMedium)
            }
            // You can add an Image here or any other UI elements
        }
    }
}