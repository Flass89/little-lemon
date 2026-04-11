package com.example.littlelemon // Ensure this matches your package name

import io.ktor.http.*

import androidx.lifecycle.lifecycleScope
import androidx.compose.runtime.livedata.observeAsState
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.coroutines.launch
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme // Your project's theme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

// ... imports
class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            }, contentType = ContentType.Any) // <--- THIS IS THE FIX for GitHub Raw files
        }
    }
    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                val menuItemsNetwork = fetchMenu()
                storeMenuInDatabase(menuItemsNetwork)
            }
        }

        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                MyNavigation(navController, database)
            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        // Use .body<MenuNetwork>() to explicitly tell Ktor what to transform into
        val response: MenuNetwork = httpClient.get(url).body()
        return response.menu
    }

    private fun storeMenuInDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}

// Extension function to map Network model to Room model
fun MenuItemNetwork.toMenuItemRoom() = MenuItemRoom(id, title, description, price, image, category)

