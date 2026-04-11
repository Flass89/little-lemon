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
    // Retrieve menu items from the database as a state
    val menuItems by database.menuItemDao().getAll().observeAsState(emptyList())

    // Search state (for future search functionality)
    var searchPhrase by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // --- STEP 1: HEADER ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(50.dp)) // To center the logo

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.height(40.dp).weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate(Profile.route) }
            )
        }

        // --- STEP 4: HERO SECTION ---
        Column(
            modifier = Modifier
                .background(Color(0xFF495E57))
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
        ) {
            Text(
                text = "Little Lemon",
                fontSize = 40.sp,
                color = Color(0xFFF4CE14),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Chicago",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(0.6f)
                )
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "Hero Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Search Bar (Instruction Step 4)
            TextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                placeholder = { Text("Enter search phrase") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFEDEFEE))
            )
        }

        // --- CATEGORY FILTERS (Mockup UI) ---
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "ORDER FOR DELIVERY!",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 30.dp, bottom = 10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryButton("Starters")
                CategoryButton("Mains")
                CategoryButton("Desserts")
                CategoryButton("Drinks")
            }
            Divider(color = Color.LightGray, thickness = 1.dp)
        }

        // --- STEP 5: MENU ITEMS LIST ---
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            items(menuItems) { menuItem ->
                MenuRow(menuItem)
            }
        }
    }
}

@Composable
fun CategoryButton(category: String) {
    Surface(
        color = Color(0xFFEDEFEE),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = category,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF495E57),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuRow(item: MenuItemRoom) {
    Column(modifier = Modifier.clickable { /* Future click logic */ }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(0.7f)) {
                Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    text = item.description,
                    color = Color.Gray,
                    maxLines = 2,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Text(
                    text = "$${item.price}",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF495E57)
                )
            }

            // Using Glide to load the network image URL
            GlideImage(
                model = item.image,
                contentDescription = item.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Divider(color = Color(0xFFEDEFEE), thickness = 1.dp)
    }
}