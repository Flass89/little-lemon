package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Onboarding() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // --- LOGO SECTION (Large) ---
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(150.dp)
                .padding(vertical = 20.dp)
        )

        // --- BANNER SECTION ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF495E57)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                fontSize = 24.sp,
                color = Color.White
            )
        }

        // --- FORM SECTION ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Personal information",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            // First Name
            InputLabel("First name")
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = { Text("Tilly") }, // <--- HINT ADDED BACK
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(8.dp)
            )

            // Last Name
            InputLabel("Last name")
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = { Text("Doe") }, // <--- HINT ADDED BACK
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(8.dp)
            )

            // Email
            InputLabel("Email")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("tillydoe@example.com") }, // <--- HINT ADDED BACK
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(8.dp)
            )
        }

        // --- REGISTER BUTTON ---
        Button(
            onClick = { /* Logic for next lab */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4CE14)
            )
        ) {
            Text(text = "Register", color = Color.Black)
        }
    }
}

// Helper composable for consistent labels
@Composable
fun InputLabel(label: String) {
    Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 4.dp),
        color = Color.Gray
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding()
}