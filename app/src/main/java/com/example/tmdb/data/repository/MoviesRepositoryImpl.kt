package com.example.tmdb.data.repository

import android.util.Log
import com.example.tmdb.data.local.MoviesLocalDataSource
import com.example.tmdb.data.local.model.MoviesEntity
import com.example.tmdb.data.local.model.PersonalityEntity
import com.example.tmdb.data.mapper.toMovies
import com.example.tmdb.data.mapper.toPersonality
import com.example.tmdb.data.mapper.toPersonalityEntity
import com.example.tmdb.data.remote.MoviesRemoteDataSource
import com.example.tmdb.data.remote.models.MoviesDto
import com.example.tmdb.domain.Category
import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.Personality
import com.example.tmdb.domain.Result
import com.example.tmdb.domain.map
import com.example.tmdb.domain.repository.MoviesRepository
import com.example.tmdb.domain.resolveCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor (
    private val localDataSource: MoviesLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    private val remoteDataSource: MoviesRemoteDataSource
): MoviesRepository {
    val tag = "MOVIES_REPOSITORY_IMPL"
    val moviesFlow = localDataSource.movies.map { it.map { movie->
        movie.toMovies(
        genre = localDataSource.getGenreForMovie(movie.id),
        category = localDataSource.getCategoryForMovie(movie.id) )
    } }

    suspend fun syncMovieSearch(query: String): Result<MoviesEntity, DataErrors.RemoteError>?{
        Log.d(tag,"Getting data from the net")
        val response = remoteDataSource.searchForMovieBasedOnQuery(query)
        when(response){
            is Result.Error -> return response
            is Result.Success -> {
                localDataSource.addMovies(movieList = response.data, categoryId = 1)
                return null
            }
        }
    }

    suspend fun syncPersonalitySearch(query:String): Result<PersonalityEntity, DataErrors.RemoteError>?{
        val response = remoteDataSource.searchForPersonalityBasedOnQuery(query)
        return when(response){
            is Result.Error -> return response
            is Result.Success -> {
                response.data.results.forEach { actor ->
                    if (actor.known_for != null) localDataSource.addMovies(movieList = actor.known_for)
                    actor.known_for?.forEach {
                        localDataSource.addPersonality(actor.toPersonalityEntity(it.id))
                    } }
                return null
            }
        }
    }

    suspend fun searchMovie(query: String): Result<List<Movies>, DataErrors>{
        var searchResponse = localDataSource.searchMovie(query)

        when(searchResponse){
            is Result.Error -> {
                Log.d(tag,"Nothing found in the database")
                syncMovieSearch(query)
                searchResponse = localDataSource.searchMovie(query)
            }
            is Result.Success -> {
                return Result.Success(searchResponse.data.map {movie->
                    movie.toMovies(
                    genre = localDataSource.getGenreForMovie(movie.id),
                    category = localDataSource.getCategoryForMovie(movie.id) )
                })
            }
        }
        return searchResponse.map {
            it.map { movie->
                movie.toMovies(
                genre = localDataSource.getGenreForMovie(movie.id),
                category = localDataSource.getCategoryForMovie(movie.id)
            ) }
        }
    }

    suspend fun searchPerson(query: String): Result<List<Personality>, DataErrors>{
        var searchResponse = localDataSource.searchPersonality(query)


        when (searchResponse){
            is Result.Error -> {
                syncPersonalitySearch(query)
                searchResponse =localDataSource.searchPersonality(query)
            }
            is Result.Success ->{
                return Result.Success(searchResponse.data.map { personality->
                    personality.toPersonality(localDataSource.moviesForPersonality(personality.actorId)) })
            }

        }
        return searchResponse.map { it.map { personality ->
            personality.toPersonality(localDataSource.moviesForPersonality(personality.actorId))
        } }
    }

    suspend fun bookMarkMovie(movieId:Int,bookMark: Boolean){
        localDataSource.bookMark(bookMark,movieId)
    }

    suspend fun getMovies():Result<Flow<List<Movies>>, DataErrors>{
        return if (localDataSource.notEmptyTable()) Result.Success(moviesFlow)
         else {
            dataSync(1,Category.NowPlaying)
            dataSync(1,Category.UpComing)
            dataSync(1, Category.TopRated)
            dataSync(1, Category.Popular)

            Log.d("MoviesRepoImpl","table empty making online request")
            Result.Success(moviesFlow)

        }
    }

    suspend fun dataSync(
        page: Int,
        category: Category
    ){
        val movies = remoteDataSource.getMovies(page = page, category)
        when(movies.second){
            is Result.Error -> { Log.d("MovieRepoImp","Error + ${(movies.second as Result.Error<DataErrors.RemoteError>).error}")}
            is Result.Success -> {
                localDataSource.addMovies(
                    movieList = (movies.second as Result.Success<MoviesDto>).data,
                    categoryId = resolveCategory(movies.first)
                )
            }
        }
    }
}