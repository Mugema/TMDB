package com.example.tmdb.presentation.discoverScreen

import android.util.Log
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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

    private var categorizedMovies= mutableListOf<Movie>()

    var searchValue=""

    private val _discoverScreenState = MutableStateFlow(DiscoverScreenState())
    val discoverScreenState = _discoverScreenState.onStart {  }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            DiscoverScreenState()
        )

    fun onValueChange(){

    }

    init {
        viewModelScope.launch {
            val ll= mutableListOf<Movie>()
            delay(200L)
            repository.getMovie().collect{
                ll.add(it.toMovie())
                Log.d("discoverViewModel",ll.toString())
                Log.d("discoverViewModel it",it.toString())
                _movies.value=ll
            }
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
                moviesFilter(1)
            }
            "topRated"->{
                _filterChipState.value=_filterChipState.value.copy(
                    topRated = !_filterChipState.value.topRated)
                moviesFilter(3)
            }
            "upComing"->{
                _filterChipState.value=_filterChipState.value.copy(
                    upComing = !_filterChipState.value.upComing)
                moviesFilter(2)
            }
            "popular"->{
                _filterChipState.value=_filterChipState.value.copy(
                    popular = !_filterChipState.value.popular)
                moviesFilter(4)

            }
        }
    }

    private fun moviesFilter(category:Int){
        viewModelScope.launch {
            repository.getMovie().filter {movie->
                category in movie.category
            }
                .collect{
                    movie-> categorizedMovies.add(movie.toMovie())
                }
        }
    }

    fun onBookmarkClick(id: Int){
        viewModelScope.launch {
            repository.bookMark(_iconState.value.addBookmark,id)
        }
    }

}

data class DiscoverScreenState(
    val searchQuery:String="",
    //val iconState: IconState=IconState(),
    val filterChipState: FilterChipState=FilterChipState(),
    val movies: List<Movie> = emptyList()
)


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