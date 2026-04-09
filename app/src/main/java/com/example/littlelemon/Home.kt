package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Step 4: Header with Logo in center and Profile icon on right
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Spacer to balance the layout (Logo in center)
            Spacer(modifier = Modifier.width(80.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.height(60.dp)
                    .fillMaxWidth(0.6f)

            )


            // Profile Image serves as a button
            Image(
                painter = painterResource(id = R.drawable.profile), // Make sure profile.png is in drawable
                contentDescription = "Profile",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clickable {
                        // Navigate to Profile screen
                        navController.navigate(Profile.route)
                    }
            )
        }

        // Rest of Home Screen content...
    }
}