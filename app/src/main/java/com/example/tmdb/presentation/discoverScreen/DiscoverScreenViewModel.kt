package com.example.tmdb.presentation.discoverScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.repository.TMDBRepositoryImpl
import com.example.tmdb.domain.Movies
import com.example.tmdb.presentation.Event
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.presentation.models.toMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    private var _movies= MutableStateFlow(mutableListOf<Movie>())
    val movie=_movies.asStateFlow()

    var unCategorisedMovies= mutableListOf<Movie>()

    private var popularMovies= mutableListOf<Movie>()

    private var nowPlayingMovies=mutableListOf<Movie>()

    private var upComingMovies=mutableListOf<Movie>()

    private var topRatedMoves= mutableListOf<Movie>()

    init {
        viewModelScope.launch {
            delay(200L)
            _movies.value=getMovieData().toMutableList()
        }
    }
    fun onEvent(event: Event){
        when(event){
            Event.VISIBLE ->{
                _iconState.value=_iconState.value.copy(showOverview = !_iconState.value.showOverview)
            }
            Event.BOOKMARK ->{
                _iconState.value=_iconState.value.copy(addBookmark = !_iconState.value.addBookmark)
            }
        }
    }

    fun onChipStateChange(chip:String){
        when(chip){
            "nowPlaying"->{
                _filterChipState.value=_filterChipState.value.copy(
                    nowPlaying = !_filterChipState.value.nowPlaying)
                moviesFilter()
            }
            "topRated"->{
                _filterChipState.value=_filterChipState.value.copy(
                    topRated = !_filterChipState.value.topRated)
                moviesFilter()
            }
            "upComing"->{
                _filterChipState.value=_filterChipState.value.copy(
                    upComing = !_filterChipState.value.upComing)
                moviesFilter()
            }
            "popular"->{
                _filterChipState.value=_filterChipState.value.copy(
                    popular = !_filterChipState.value.popular)
                moviesFilter()

            }
        }
    }

    private fun moviesFilter(){
        _movies.value= mutableListOf()
        if (_filterChipState.value.popular) _movies.value.addAll(popularMovies)
        if (_filterChipState.value.topRated) _movies.value.addAll(topRatedMoves)
        if (_filterChipState.value.nowPlaying) _movies.value.addAll(nowPlayingMovies)
        if (_filterChipState.value.upComing) _movies.value.addAll(upComingMovies)
    }

    private fun getMoviesUnderCategory(id:Int):Deferred<MutableList<Movie>>{
        var popular: List<Movies>
        val popularList = mutableListOf<Movie>()
        return viewModelScope.async {
            popular=repository.getMovieCategory(id)
            popular.forEach {
                    movies -> popularList.add(movies.toMovie())
            }
            popularList
        }
    }

    private fun getMovieData():List<Movie>{
        viewModelScope.launch(Dispatchers.Default) {
            nowPlayingMovies=getMoviesUnderCategory(1).await().also {
                unCategorisedMovies.addAll(it)
            }
            upComingMovies=getMoviesUnderCategory(2).await().also {
                unCategorisedMovies.addAll(it)
            }
            topRatedMoves=getMoviesUnderCategory(3).await().also {
                unCategorisedMovies.addAll(it)
            }
            popularMovies=getMoviesUnderCategory(4).await().also {
                unCategorisedMovies.addAll(it)
            }
        }
        return unCategorisedMovies
    }

    fun onBookmarkClick(id: Int){
        viewModelScope.launch {
            repository.bookMark(_iconState.value.addBookmark,id)
        }
    }

}

data class IconState(
    val showOverview:Boolean=false,
    val addBookmark:Boolean=false
)

data class FilterChipState(
    val nowPlaying:Boolean=false,
    val topRated:Boolean=false,
    val upComing:Boolean=false,
    val popular:Boolean=false,
)