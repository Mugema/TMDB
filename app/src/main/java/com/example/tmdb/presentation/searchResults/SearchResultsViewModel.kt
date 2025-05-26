package com.example.tmdb.presentation.searchResults

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.repository.TMDBRepositoryImpl
import com.example.tmdb.domain.Result
import com.example.tmdb.presentation.models.Actor
import com.example.tmdb.presentation.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val repository: TMDBRepositoryImpl
): ViewModel() {

    private val _searchResults = MutableStateFlow(SearchResultsScreenState())
    val searchResults = _searchResults.asStateFlow()

    private val _chipState = MutableStateFlow(ChipState())
    val chipState = _chipState.asStateFlow()

    fun updateSearchQuery(query:String){
        _searchResults.update { it.copy(searchQuery = query) }
    }

    fun searchMovie(){
        _searchResults.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = repository.searchMovie(_searchResults.value.searchQuery)
            when(response){
                is Result.Error -> {}
                is Result.Success -> _searchResults.update { it.copy(movies = response.data) }
            }
        }
        _searchResults.update { it.copy(isLoading = false) }
    }

    fun searchActor(){
        _searchResults.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = repository.searchActor(_searchResults.value.searchQuery)
            when(response){
                is Result.Error -> {}
                is Result.Success -> _searchResults.update { it.copy(actors = response.data) }
            }
        }
    }

    fun handleIntent(intent: SearchResultIntents){
        when(intent){
            is SearchResultIntents.ChipClicked -> chipIntent(intent.chip)
        }
    }

    fun chipIntent(chip: Showing){
        when(chip){
            Showing.Movie -> _chipState.update { it.copy(movie = !_chipState.value.movie) }
            Showing.Tv -> _chipState.update { it.copy(tv = !_chipState.value.tv) }
            Showing.Actor -> _chipState.update { it.copy(actor = !_chipState.value.actor) }
        }
    }

}

data class SearchResultsScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val movies:List<Movie> = emptyList<Movie>(),
    val actors: List<Actor> = emptyList<Actor>(),
    val showing: Showing = Showing.Movie
)

data class ChipState(
    val movie: Boolean = true,
    val actor: Boolean = false,
    val tv: Boolean = false

)