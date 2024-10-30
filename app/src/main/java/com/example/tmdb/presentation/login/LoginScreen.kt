package com.example.tmdb.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tmdb.R
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun LoginScreen(modifier: Modifier =Modifier){
    Box(
        modifier.fillMaxSize()
    ){
        Image(painterResource(R.drawable._e5baaaese26fej7uhcjogee2t2),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.blur(10.dp))
        Login(modifier.align(Alignment.Center))

    }
}

@Composable
fun Login(modifier: Modifier=Modifier){
    Column(modifier) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {Text("Username", color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Bold)},
            colors = OutlinedTextFieldDefaults.colors().copy(focusedTextColor = Color.Yellow)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {Text("Password")}
        )
    }
}

@Composable
@PreviewLightDark
fun loginScreenPreview(){
    TMDBTheme {
        LoginScreen()
    }
}