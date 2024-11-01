package com.example.tmdb.presentation.signUpScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tmdb.R
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun SignUpScreen(toLogin:()->Unit,onSignUpClick:()->Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Spacer(Modifier.height(16.dp))
        Image(
            painterResource(R.drawable.logos),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = "",
            label = { Text("First Name", color = MaterialTheme.colorScheme.onSurface) },
            onValueChange = {},
            modifier = Modifier.padding(start = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            label = { Text("Email", color = MaterialTheme.colorScheme.onSurface) },
            onValueChange = {},
            modifier = Modifier.padding(start = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            label = { Text("Username", color = MaterialTheme.colorScheme.onSurface) },
            onValueChange = {},
            modifier = Modifier.padding(start = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            label = { Text("Password", color = MaterialTheme.colorScheme.onSurface) },
            onValueChange = {},
            modifier = Modifier.padding(start = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { onSignUpClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("SignUp")
        }
        TextButton(onClick = {toLogin()}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Already Have an account")
        }

    }
}

@PreviewLightDark
@Composable
fun PreviewSignUpScreen(){
    TMDBTheme {
        SignUpScreen({}) {  }
    }
}