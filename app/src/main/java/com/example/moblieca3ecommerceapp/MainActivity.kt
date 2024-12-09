package com.example.moblieca3ecommerceapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.moblieca3ecommerceapp.ui.theme.MoblieCA3ecommerceappTheme


data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val imageUrl: String
)

val sampleProducts = listOf(
    Product(id = 1, name = "Baby Shirt", price = "$15", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 2, name = "Baby Pants", price = "$20", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 3, name = "Baby Shoes", price = "$25", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 4, name = "Baby Shirt", price = "$15", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 5, name = "Baby Pants", price = "$20", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 6, name = "Baby Shirt", price = "$15", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 7, name = "Baby Pants", price = "$20", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 8, name = "Baby Shirt", price = "$15", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 9, name = "Baby Pants", price = "$20", imageUrl = "https://via.placeholder.com/150"),
    Product(id = 10, name = "Baby Shirt", price = "$15", imageUrl = "https://via.placeholder.com/150"),

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(navController: NavHostController) { // Renamed HomeScreen to ShopScreen
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Snuggle Wear") })
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding, // Ensures the content doesn't overlap with the TopAppBar
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sampleProducts) { product ->
                ProductCard(product) {
                    navController.navigate("details/${product.id}")
                }
            }
        }
    }
}
@Composable
fun HomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center all elements
        ) {
            // Circular Image
            Image(
                painter = rememberImagePainter(data = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmiL5ThV248tPrHxEy5AeA4nI6bwMkaSufSg&s/300"),
                contentDescription = "Welcome Image",
                modifier = Modifier
                    .size(200.dp) // Set width and height (image will be square before clipping)
                    .clip(CircleShape) // Apply circular shape
                    .padding(8.dp) // Optional: padding around the circle
            )

            // Welcome Text
            Text(
                text = "Welcome to SnuggleWear!",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Spacer to separate the text and button
            Spacer(modifier = Modifier.height(16.dp))

            // Shop Now Button
            Button(onClick = {
                navController.navigate("shop") // Navigate to ShopScreen
            }) {
                Text("Shop Now")
            }
        }
    }
}


@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = product.imageUrl),
                contentDescription = "Product Image",
                modifier = Modifier.size(60.dp).padding(end = 16.dp)
            )
            Column {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(text = product.price, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun ProductDetailScreen(productId: Int) {
    val product = sampleProducts.firstOrNull { it.id == productId }
    if (product != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = product.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = product.price, style = MaterialTheme.typography.titleLarge)
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Product not found", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) } // Welcome Screen
        composable("shop") { ShopScreen(navController) } // Shop Screen
        composable("details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
            ProductDetailScreen(productId)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoblieCA3ecommerceappTheme {
                AppNavigation()
            }
        }
    }
}
