package com.example.tmdb.presentation.searchResults

import android.util.Log
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
        searchMovie()
    }

    private fun searchMovie(){
        _searchResults.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = repository.searchMovie(_searchResults.value.searchQuery)
            when(response){
                is Result.Error -> {}
                is Result.Success -> _searchResults.update { it.copy(movies = response.data) }
            }
            Log.d("SearchResultVM","searching for movie")
        }
        _searchResults.update { it.copy(isLoading = false) }
    }

    private fun searchActor(){
        _searchResults.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = repository.searchActor(_searchResults.value.searchQuery)
            when(response){
                is Result.Error -> {}
                is Result.Success -> _searchResults.update { it.copy(actors = response.data) }
            }
        }
        Log.d("SearchResultVM","searching for actor")
        _searchResults.update { it.copy(isLoading = false) }
    }

    fun handleIntent(intent: SearchResultIntents){
        when(intent){
            is SearchResultIntents.ChipClicked -> chipIntent(intent.chip)
            is SearchResultIntents.ActorClicked -> {
                _searchResults.update { it.copy(chosenActor = intent.id) }
                val movies = _searchResults.value.actors.filter { it.id == _searchResults.value.chosenActor }.map{it.knownFor}.flatten()
                Log.d("ResultsViewmodel", movies.size.toString() )
                Log.d("ResultsViewmodel", movies.toString() )
            }
        }
    }

    fun chipIntent(chip: Showing){
        when(chip){
            Showing.Movie -> {
                _chipState.update { it.copy(movie = !_chipState.value.movie,false,false) }

                if (_chipState.value.movie) {
                    _searchResults.update { it.copy(showing = Showing.Movie) }
                    searchMovie()
                }
            }
            Showing.Tv -> {
                _chipState.update {
                    it.copy(
                        tv = !_chipState.value.tv,
                        actor = false,
                        movie = false
                    )
                }
            }
            Showing.Actor -> {
                _chipState.update { it.copy(
                    actor = !_chipState.value.actor,
                    movie = false,
                    tv = false,)
                }
                if (_chipState.value.actor) {
                    _searchResults.update { it.copy(showing = Showing.Actor) }
                    searchActor()
                }
            }
        }
    }
}

data class SearchResultsScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val movies:List<Movie> = emptyList<Movie>(),
    val actors: List<Actor> = emptyList<Actor>(),
    val showing: Showing = Showing.Movie,
    val chosenActor:Int = 0
)

data class ChipState(
    val movie: Boolean = true,
    val actor: Boolean = false,
    val tv: Boolean = false

)