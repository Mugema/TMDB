package com.example.tmdb.presentation.showMovieDetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.tmdb.R
import com.example.tmdb.presentation.components.BoxedText
import com.example.tmdb.presentation.components.LoadingAnimation
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.ui.theme.TMDBTheme
import java.util.Locale

@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    viewModel: ShowMovieDetailsViewModel = hiltViewModel<ShowMovieDetailsViewModel>(),
    movieId:Int=0
) {
    LaunchedEffect(true) {
        viewModel.getMovie(movieId)
    }

    val movieData by viewModel.selectedMovie.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    var imageResult by remember { mutableStateOf<Result<Painter>?>(null) }

    Log.d("model","https://image.tmdb.org/t/p/original${movieData.backDrop}")

    Log.d("movieDetails","$movieData")

    val painter = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/original${movieData.backDrop}",
        onSuccess = { imageResult = Result.success(it.painter) },
        onError = { imageResult = Result.failure(it.result.throwable) }
    )


    Column(
        modifier= modifier.background(Color.White)
            .fillMaxSize()
    ) {
        when(val result = imageResult) {
            null -> LoadingAnimation(Modifier.fillMaxWidth().clip(RectangleShape))
            else -> {

                    Image(
                        painter = if (result.isSuccess) painter else painterResource(R.drawable.no_image),
                        contentDescription = null,
                    )

            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier= Modifier
                .background(Color.White)
                .padding(start = 8.dp, end = 8.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(movieData.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                        Box(
                            modifier = Modifier
                                .shadow(8.dp, CircleShape, ambientColor = Color.Black, spotColor = Color.White)
                                .clip(CircleShape)


                        ){
                            Text(String.format(Locale.US,"%.1f",movieData.rating),modifier=Modifier)
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(movieData.date,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(movieData.adult,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold)
                        Text(movieData.originalLanguage,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
                Spacer(Modifier.weight(1f))
                IconButton( onClick = {viewModel.bookMark(bookMark = !movieData.bookmark,id=movieData.id )} ) {
                    Icon(
                        imageVector = if ( movieData.bookmark) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = null
                    )
                }
            }
            BoxedText(movieData.genre,
                modifier= Modifier
                .clip(RoundedCornerShape(20))
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp))
            Text(movieData.overview, textAlign = TextAlign.Justify)

        }
    }
}


val movie = Movie(
    title = "A Minecraft Movie",
    overview = "Four misfits find themselves struggling with ordinary problems when they are suddenly pulled through a mysterious portal into the Overworld: a bizarre, cubic wonderland that thrives on imagination. To get back home, they'll have to master this world while embarking on a magical quest with an unexpected, expert crafter, Steve.",
    image = "iii",
    genre = listOf("Movie","Thriller","Fantasy"),
    rating = 6.5,
    date = "2025",
    bookmark = false,
    id = 1,
    category = listOf(1)
)




@Preview
@Composable
private fun PreviewMovieDetails() {
    TMDBTheme {
        MovieDetails()
    }
    
}