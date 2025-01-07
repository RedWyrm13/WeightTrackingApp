package com.example.weighttracking.googleauthentication

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.weighttracking.ui.theme.viewmodel.UserViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.Base64
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
@Composable
fun GoogleSignInButton(userViewModel: UserViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick:() -> Unit = {
        val webClientID = "504828019130-03qnqdop8r7u5fce5q3emjf3talgcmjs.apps.googleusercontent.com"
        val credentialManager = CredentialManager.create(context)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientID)
            .setAutoSelectEnabled(true)
            .setNonce(generateNonce())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()
                Log.d("GoogleSignInButton", "Google ID token: ${result.credential.data}")
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    MainContentOfGoogleSignInButton(userViewModel = userViewModel, onClick = onClick)

}
@Composable
fun MainContentOfGoogleSignInButton(userViewModel: UserViewModel,
                                    onClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val selectedUnit = remember { mutableStateOf("lbs") }

        Text("Welcome to Weight Tracking, Please Enter Your Current Weight")

        TextFieldForSignIn(userViewModel = userViewModel)


        // Row for Unit Selection Boxes
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LbsBox(selectedUnit.value == "lbs") { selectedUnit.value = "lbs" }
            KgBox(selectedUnit.value == "kg") { selectedUnit.value = "kg" }
        }

        Button(onClick = onClick) {
            Text("Continue With Google")
        }
    }

}
@Composable
fun TextFieldForSignIn(userViewModel: UserViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = userViewModel.initialWeight.value,
        onValueChange = { userViewModel.initialWeight.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {keyboardController?.hide()}
        ),
        label = { Text("Enter Weight") }
    )

}

@Composable
fun LbsBox(isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(
                if (isSelected) Color.Blue else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "lbs", color = Color.White)
    }
}

@Composable
fun KgBox(isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(
                if (isSelected) Color.Blue else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "kg", color = Color.White)
    }
}

@Preview
@Composable
fun GoogleSignInButtonPreview() {
        GoogleSignInButton(UserViewModel())
}


fun generateNonce(length: Int = 16): String {
    val secureRandom = SecureRandom() // Cryptographically strong random number generator
    val nonceBytes = ByteArray(length)
    secureRandom.nextBytes(nonceBytes) // Fill the array with random bytes
    return Base64.getUrlEncoder().withoutPadding().encodeToString(nonceBytes)
}