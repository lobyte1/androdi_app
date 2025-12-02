package com.example.duocappmoviles003d.vistas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    // Creamos el NavController
                    val navController = rememberNavController()

                    // Iniciamos el NavigationHost
                    NavigationHost(navController = navController)
                }
            }
        }
    }
}