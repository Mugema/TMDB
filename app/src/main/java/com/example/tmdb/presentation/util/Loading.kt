package com.example.tmdb.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tmdb.R

@Composable
fun Loading(){
    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(R.drawable.logo), contentDescription = null,
            modifier = Modifier.align(Alignment.Center))
        LinearProgressIndicator(trackColor = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.width(128.dp).align(Alignment.Center))
    }
}