package com.example.tmdb.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BoxedText(
    text: List<String>,
    modifier:Modifier= Modifier
){
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        text.forEach {
            Box(
                modifier =modifier
            ){
                Text(it, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}