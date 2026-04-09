package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Profile(navController: NavHostController) {
    // Step 2: Retrieve user data from SharedPreferences
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Step 1: Header with Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(150.dp)
                .padding(vertical = 20.dp)
        )

        // Step 2: Display User Data
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .weight(1f)
        ) {
            Text(
                text = "Personal information",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 40.dp)
            )

            // First Name Display
            ProfileItemLabel("First name")
            ProfileDataBox(firstName)

            // Last Name Display
            ProfileItemLabel("Last name")
            ProfileDataBox(lastName)

            // Email Display
            ProfileItemLabel("Email")
            ProfileDataBox(email)
        }

        // Step 3: Log out Button
        Button(
            onClick = {
                // Clear all data
                sharedPreferences.edit().clear().apply()
                // Navigate back to Onboarding
                navController.navigate(Onboarding.route) {
                    // Clear the backstack so user can't go back to Profile
                    popUpTo(Home.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
        ) {
            Text(text = "Log out", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileItemLabel(label: String) {
    Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun ProfileDataBox(value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        enabled = false, // Makes it look like the mockup (read-only)
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledBorderColor = Color.LightGray,
            disabledLabelColor = Color.Gray,
            disabledPlaceholderColor = Color.Gray

        )
    )
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    Profile(navController)
}