package com.example.tmdb.presentation.components

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun LoadingAnimation(modifier: Modifier= Modifier){
    val infiniteTransition = rememberInfiniteTransition()

    val colorFloat = infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 1f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1000,easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val backgroundGradient = Brush.horizontalGradient(
        0.1f to Color.Gray,
        colorFloat.value to Color.White,
        1f to Color.Gray
        )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .height(256.dp)
            .width(128.dp)
            .background(backgroundGradient)
    )
}

@Preview
@Composable
private fun LoadingAnimationPreview() {
    TMDBTheme {
        LoadingAnimation(modifier = Modifier)
    }

}