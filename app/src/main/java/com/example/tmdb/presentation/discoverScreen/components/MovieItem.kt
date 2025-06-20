package com.example.tmdb.presentation.discoverScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.tmdb.R
import com.example.tmdb.presentation.components.LoadingAnimation
import com.example.tmdb.presentation.models.Movie


@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    toMovieDetails:(movie: Movie)->Unit={}
){
    var imageResult by remember { mutableStateOf<Result<Painter>?>(null) }

    val painter = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/original/${movie.image}",
        onSuccess = { imageResult = Result.success(it.painter) },
        onError = { imageResult = Result.failure(it.result.throwable) }
    )

    when(val result = imageResult){
        null -> LoadingAnimation(Modifier.clip(RoundedCornerShape(10)))
        else -> {
            Box{
                Image(
                    painter = if (result.isSuccess) painter else painterResource(R.drawable.no_image),
                    contentDescription = null,
                    modifier = modifier
                        .clip(RoundedCornerShape(10))
                        .clickable { toMovieDetails(movie) }
                        .border(width = 1.dp, Color.Black, RoundedCornerShape(10))
                        .graphicsLayer{ compositingStrategy = CompositingStrategy.Offscreen }
                )
            }
        }
    }
}

fun Modifier.blendMode(blendMode: BlendMode): Modifier {
    return this.drawWithCache {
        val graphicsLayer = obtainGraphicsLayer()
        graphicsLayer.apply {
            record {
                drawContent()
            }
            this.blendMode=blendMode
        }
        onDrawWithContent {
            drawLayer(graphicsLayer)
        }

    }
}