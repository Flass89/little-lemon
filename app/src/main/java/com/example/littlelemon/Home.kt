package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    // Retrieve menu items from the database
    val menuItems by database.menuItemDao().getAll().observeAsState(emptyList())

    // STEP 1: Define search state
    var searchPhrase by remember { mutableStateOf("") }

    // STEP 2: Define category filter state
    var categoryFilter by remember { mutableStateOf("") }

    // Logic: Group items by category to get unique category names for the breakdown
    val categories = menuItems.map { it.category }.distinct()

    // Logic: Filter items based on BOTH Search Phrase and Selected Category
    val filteredItems = menuItems.filter {
        it.title.contains(searchPhrase, ignoreCase = true)
    }.filter {
        if (categoryFilter.isEmpty()) true else it.category == categoryFilter
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // --- HEADER ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(50.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.height(40.dp).weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier.size(50.dp).clickable { navController.navigate(Profile.route) }
            )
        }

        // --- HERO SECTION ---
        Column(
            modifier = Modifier
                .background(Color(0xFF495E57))
                .padding(20.dp)
        ) {
            Text("Little Lemon", fontSize = 40.sp, color = Color(0xFFF4CE14), fontWeight = FontWeight.Bold)
            Text("Chicago", fontSize = 24.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
                Text(
                    "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(0.6f)
                )
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "Hero",
                    modifier = Modifier.size(100.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // STEP 1: Search TextField (Material 2)
            TextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                placeholder = { Text("Enter search phrase") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFEDEFEE),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // --- MENU BREAKDOWN SECTION ---
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                "ORDER FOR DELIVERY!",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )

            // STEP 2: Category Buttons (Breakdown)
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Manually listing categories to match common menu types or using the dynamic list
                val displayCategories = listOf("Starters", "Mains", "Desserts", "Drinks")

                displayCategories.forEach { categoryName ->
                    CategoryChip(
                        categoryName = categoryName,
                        isSelected = categoryFilter.equals(categoryName, ignoreCase = true),
                        onTapped = {
                            // If user clicks the same category again, clear the filter
                            categoryFilter = if (categoryFilter.equals(categoryName, ignoreCase = true)) "" else categoryName.lowercase()
                        }
                    )
                }
            }
            Divider(color = Color.LightGray, thickness = 1.dp)
        }

        // --- MENU LIST ---
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            items(filteredItems) { item ->
                MenuRow(item)
            }
        }
    }
}

@Composable
fun CategoryChip(categoryName: String, isSelected: Boolean, onTapped: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onTapped() },
        // Change color if selected to give user feedback
        color = if (isSelected) Color(0xFF495E57) else Color(0xFFEDEFEE),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = categoryName,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else Color(0xFF495E57),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuRow(item: MenuItemRoom) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = item.description, color = Color.Gray, maxLines = 2, modifier = Modifier.padding(vertical = 5.dp))
            Text(text = "$${item.price}", fontWeight = FontWeight.SemiBold, color = Color(0xFF495E57))
        }
        GlideImage(
            model = item.image,
            contentDescription = item.title,
            modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
    Divider(color = Color(0xFFEDEFEE), thickness = 1.dp)
}