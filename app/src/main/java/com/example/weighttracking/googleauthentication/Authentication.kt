package com.example.weighttracking.googleauthentication

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.Base64

@Composable
fun GoogleSignInButton() {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick = {
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

                val credential = result.credential
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    Button(onClick = { onClick() }) {

        Text("Sign in with Google")
    }
}

fun generateNonce(length: Int = 16): String {
    val secureRandom = SecureRandom() // Cryptographically strong random number generator
    val nonceBytes = ByteArray(length)
    secureRandom.nextBytes(nonceBytes) // Fill the array with random bytes
    return Base64.getUrlEncoder().withoutPadding().encodeToString(nonceBytes)
}