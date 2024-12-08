package com.example.moblieca3ecommerceapp

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moblieca3ecommerceapp.ui.screens.HomeScreen
import com.example.moblieca3ecommerceapp.ui.theme.MoblieCA3ecommerceappTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply the theme to your app
            MoblieCA3ecommerceappTheme {
                // Root composable for your app
                HomeScreen()
            }
        }
    }
}