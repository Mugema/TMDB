package com.example.tmdb.presentation.discoverScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.repository.TMDBRepositoryImpl
import com.example.tmdb.domain.Category
import com.example.tmdb.domain.resolveCategory
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.presentation.models.toMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val repository:TMDBRepositoryImpl
):ViewModel() {

    private val _discoverScreenState = MutableStateFlow(DiscoverScreenState())
    val discoverScreenState = _discoverScreenState.onStart {
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        DiscoverScreenState()
    )

    private val _filterChipState = MutableStateFlow( FilterChipState() )
    val filterChipState = _filterChipState.asStateFlow()

    private var originalMovieData = mutableListOf<Movie>()

    init {
        loadData()
    }

    fun handleIntent(action: DiscoverScreenIntents) {
        Log.d("Action",action.toString())
        when (action) {
            is DiscoverScreenIntents.OnChipClicked -> onChipStateChange(action.category)

            is DiscoverScreenIntents.OnSearch -> _discoverScreenState.update { it.copy(searchQuery = action.query) }

            DiscoverScreenIntents.OnProfilePictureClicked -> TODO()

            is DiscoverScreenIntents.OnTabClicked -> {
                when(action.screen){
                    0-> {
                        Log.d("TabClicked","Movie tab clicked")
                        _discoverScreenState.update {
                            it.copy( screenTab = ScreenTab(true,false) )
                        }
                    }
                    1-> {
                        Log.d("TabClicked", "Series tab clicked")
                        _discoverScreenState.update {
                            it.copy( screenTab = ScreenTab(false,true))
                        }
                    }
                }
            }
        }

    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getMovie().collect { movieList ->
                originalMovieData = movieList.map { it.toMovie() }.toMutableList()
                _discoverScreenState.update {
                    it.copy(
                        movies = originalMovieData
                    )
                }
            }
        }
    }


    private fun onChipStateChange(category: Category) {
        Log.d("onChipChange","Before clicking" + _filterChipState.value.toString())
        val categoriesToShow = mutableListOf<Int>()
        when (category) {
            Category.NowPlaying -> {
                _filterChipState.update {
                    it.copy(nowPlaying = !_filterChipState.value.nowPlaying)
                }
                if (_filterChipState.value.nowPlaying)
                    categoriesToShow.add(resolveCategory(Category.NowPlaying))
                else categoriesToShow.remove(resolveCategory(Category.NowPlaying))

                moviesFilter(categoriesToShow)
            }

            Category.TopRated -> {
                _filterChipState.update {
                    it.copy(topRated = !_filterChipState.value.topRated)
                }
                if (_filterChipState.value.topRated)
                    categoriesToShow.add(resolveCategory(Category.TopRated))
                else categoriesToShow.remove(resolveCategory(Category.TopRated))

                moviesFilter(categoriesToShow)
            }

            Category.UpComing -> {
                _filterChipState.update {
                    it.copy(
                        upComing = !_filterChipState.value.upComing
                    )
                }

                if (_filterChipState.value.upComing)
                    categoriesToShow.add(resolveCategory(Category.UpComing))
                else categoriesToShow.remove(resolveCategory(Category.UpComing))

                moviesFilter(categoriesToShow)

            }

            Category.Popular -> {
                _filterChipState.update {
                    it.copy(popular = !_filterChipState.value.popular)
                }

                if (_filterChipState.value.popular)
                    categoriesToShow.add(resolveCategory(Category.Popular))
                else categoriesToShow.remove(resolveCategory(Category.Popular))

                moviesFilter(categoriesToShow)
            }
        }
        Log.d("onChipChange","After clicking" + _filterChipState.value.toString())
    }

    private fun moviesFilter(category: List<Int>) {
        if (category.isNotEmpty()){
            _discoverScreenState.update {
                it.copy(
                    movies = originalMovieData.filter { movie ->
                        atLeast(movie.category,category)
                    }.toMutableList()
                )
            }
        } else {
            _discoverScreenState.update {
                it.copy(
                    movies = originalMovieData.toMutableList()
                )
            }

        }
    }


    private fun atLeast(categoriesForMovie: List<Int>, list: List<Int>): Boolean {
        var containsCategory = false
        list.forEach {
            containsCategory = categoriesForMovie.contains(it)
        }
        return containsCategory
    }
}


data class DiscoverScreenState(
    val isMovieTab: Boolean = true,
    val loading: Boolean=false,
    val showMovie: Movie = Movie(),
    val searchQuery:String="",
    val movies: MutableList<Movie> = mutableListOf(),
    val filterChipState: FilterChipState = FilterChipState(),
    val screenTab: ScreenTab = ScreenTab(),
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

data class ScreenTab(
    val movies: Boolean = true,
    val series: Boolean = false
)

//Category.NowPlaying -> 1
//Category.UpComing -> 2
//Category.TopRated -> 3
//Category.Popular -> 4
