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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.moblieca3ecommerceapp.ui.theme.MoblieCA3ecommerceappTheme
import kotlinx.coroutines.delay
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: String,
    val imageUrl: String
)

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)
}

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao = AppDatabase.getDatabase(application).productDao()

    private val _allProducts = mutableStateOf<List<Product>>(emptyList())
    val allProducts: State<List<Product>> = _allProducts

    init {
        viewModelScope.launch {

            if (productDao.getAllProducts().isEmpty()) {
                sampleProducts.forEach { productDao.insertProduct(it) }
            }
            loadProducts()
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            _allProducts.value = productDao.getAllProducts()
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productDao.insertProduct(product)
            loadProducts()
        }
    }
}

//  products for testing
val sampleProducts = listOf(
    Product(name = "Baby Onesie", price = "€10.99", imageUrl = "https://faunakids.ie/cdn/shop/files/Organic-Cotton-Baby-Bodysuit-Trio-_-Bee_-Pony-_-Calf-Fauna-Kids-127364314_1400x.jpg?v=1716396049/200"),
    Product(name = "Red Hat ", price = "€15.99", imageUrl = "https://cottonkids.ie/cdn/shop/files/baby-girls-red-knitted-pram-coat-hat-set-mayoral-119267_800x.webp?v=1722506218/200"),
    Product(name = "blue gloves", price = "€8.99", imageUrl = "https://us.tartanblanketco.com/cdn/shop/files/GLKMRPLBL11-MerinoWoolKidsGlovesinBlue_c465eabd-bd07-40e6-ac12-f9cede1e5b87.jpg?v=1709033466&width=1080/200"),
    Product(name = "yellow sandals", price = "€3.99", imageUrl = "https://https://yellowbee.online/cdn/shop/files/charming-tiger-themed-sandals-for-boys-140210_1024x1024.jpg?v=1727847402/200"),

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(navController: NavHostController, productViewModel: ProductViewModel = viewModel()) {
    val showAddProductDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Snuggle Wear") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD8A7D1)
                )
            )
        },
        floatingActionButton = {
            //  Action Button for adding a product Floating
            FloatingActionButton(onClick = { showAddProductDialog.value = true }) {
                Icon(painter = painterResource(id = android.R.drawable.ic_input_add), contentDescription = "Add Product")
            }
        }
    ) { innerPadding ->
        val products = productViewModel.allProducts.value

        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No products available", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(products) { product ->
                    ProductCard(product) {
                        navController.navigate("details/${product.id}")
                    }
                }
            }
        }
    }

    // Add Product Dialog
    if (showAddProductDialog.value) {
        AddProductDialog(
            onDismiss = { showAddProductDialog.value = false },
            onAddProduct = { product ->
                productViewModel.addProduct(product)
                showAddProductDialog.value = false
            }
        )
    }
}
@Composable
fun AddProductDialog(onDismiss: () -> Unit, onAddProduct: (Product) -> Unit) {
    var name = remember { mutableStateOf("") }
    var price = remember { mutableStateOf("") }
    var imageUrl = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Add New Product") },
        text = {
            Column {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = price.value,
                    onValueChange = { price.value = it },
                    label = { Text("Product Price") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = imageUrl.value,
                    onValueChange = { imageUrl.value = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.value.isNotEmpty() && price.value.isNotEmpty() && imageUrl.value.isNotEmpty()) {
                    onAddProduct(
                        Product(
                            name = name.value,
                            price = price.value,
                            imageUrl = imageUrl.value
                        )
                    )
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
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
                    .size(200.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
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
fun ProductDetailScreen(productId: Int, productViewModel: ProductViewModel) {
    val product = productViewModel.allProducts.value.firstOrNull { it.id == productId }
    if (product != null) {
        Column(
            modifier = Modifier
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
fun AppNavigation(productViewModel: ProductViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("shop") { ShopScreen(navController, productViewModel) }
        composable("details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
            ProductDetailScreen(productId, productViewModel)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoblieCA3ecommerceappTheme {
                val productViewModel: ProductViewModel = viewModel() // Get ViewModel
                AppNavigation(productViewModel = productViewModel)
            }
        }
    }
}
