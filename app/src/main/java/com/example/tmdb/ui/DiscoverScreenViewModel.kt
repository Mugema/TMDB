package com.example.tmdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.repository.TMDBRepositoryImpl
import com.example.tmdb.domain.Movies
import com.example.tmdb.ui.models.Movie
import com.example.tmdb.ui.models.toMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val repository:TMDBRepositoryImpl
):ViewModel() {
    private var _filterChipState= MutableStateFlow(FilterChipState())
    val filterChipState=_filterChipState.asStateFlow()

    private var _iconState= MutableStateFlow(IconState())
    val iconState=_iconState.asStateFlow()

    private var _isLoading = MutableStateFlow(true)
    var isLoading=_isLoading.asStateFlow()

    private var _movieData=MutableStateFlow(listOf<Movie>())
    val movieData=_movieData.asStateFlow()

    init {
        viewModelScope.launch {
            coroutineScope {
                repository.addMovieGenre()
                repository.addMovie()
                repository.addMovieMGenreCrossRef()

                delay(2000)
                _movieData.value=getMovieData().await()
            }
            _isLoading.value=false
        }
    }
    fun onEvent(event:Event){
        when(event){
            Event.VISIBLE->{
                _iconState.value=_iconState.value.copy(showOverview = !_iconState.value.showOverview)
            }
            Event.BOOKMARK->{
                _iconState.value=_iconState.value.copy(addBookmark = !_iconState.value.addBookmark)
            }
        }
    }

    fun onChipStateChange(chip:String){
        when(chip){
            "nowPlaying"->{
                _filterChipState.value=_filterChipState.value.copy(
                    nowPlaying = !_filterChipState.value.nowPlaying)
            }
            "topRated"->{
                _filterChipState.value=_filterChipState.value.copy(
                    topRated = !_filterChipState.value.topRated)
            }
            "upComing"->{
                _filterChipState.value=_filterChipState.value.copy(
                    upComing = !_filterChipState.value.upComing)
            }

        }
    }

    fun getMovieData():Deferred<List<Movie>>{
        val movieList= mutableListOf<Movie>()
        var movieData = listOf<Movies>()
        val rr= viewModelScope.async {
                movieData=repository.getMovie()
                movieData.forEach {
                    movies -> movieList.add(movies.toMovie())
            }
           movieList
        }
        return rr
    }
}

data class IconState(
    val showOverview:Boolean=false,
    val addBookmark:Boolean=false
)

data class FilterChipState(
    val nowPlaying:Boolean=true,
    val topRated:Boolean=true,
    val upComing:Boolean=true,
)