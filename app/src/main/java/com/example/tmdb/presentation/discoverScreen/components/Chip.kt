package com.example.tmdb.presentation.discoverScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    state:Boolean,
    text:String,
    onClick:()->Unit)
{
    FilterChip(
        selected = state,
        onClick = { onClick() },
        trailingIcon = {
            Icon(
                imageVector = if (state) Icons.Default.Check
                else Icons.Default.Close,
                contentDescription = null
            )
        },
        label = { Text(text) },
        modifier = Modifier.padding(start = 2.dp),
        colors = FilterChipDefaults.filterChipColors().copy(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            labelColor = Color.Black)
    )
    Spacer(modifier = Modifier.width(4.dp))
}