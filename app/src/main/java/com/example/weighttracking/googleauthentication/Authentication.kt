package com.example.weighttracking.googleauthentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.security.SecureRandom
import java.util.Base64

@Composable
fun GoogleSignInButton() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick = {
        // Handle Google Sign-In
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var weight by remember { mutableStateOf("") }
        val selectedUnit = remember { mutableStateOf("lbs") }
        Text("Welcome to Weight Tracking, Please Enter Your Current Weight")

        TextField(
            value = weight,
            onValueChange = { weight = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            label = { Text("Enter Weight") }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lbs Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(
                        if (selectedUnit.value == "lbs") Color.Blue else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        selectedUnit.value = "lbs"
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "lbs", color = Color.White)
            }

            // Kg Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(
                        if (selectedUnit.value == "kg") Color.Blue else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        selectedUnit.value = "kg"
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "kg", color = Color.White)
            }
        }

        Button(onClick = { onClick() }) {
            Text("Continue With Google")
        }
    }
}


fun generateNonce(length: Int = 16): String {
    val secureRandom = SecureRandom() // Cryptographically strong random number generator
    val nonceBytes = ByteArray(length)
    secureRandom.nextBytes(nonceBytes) // Fill the array with random bytes
    return Base64.getUrlEncoder().withoutPadding().encodeToString(nonceBytes)
}

@Preview
@Composable
fun GoogleSignInButtonPreview() {
        GoogleSignInButton()

}