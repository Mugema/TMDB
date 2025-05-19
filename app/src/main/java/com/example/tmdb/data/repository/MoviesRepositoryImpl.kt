package com.example.tmdb.data.repository

import android.util.Log
import com.example.tmdb.data.local.MoviesLocalDataSource
import com.example.tmdb.data.mapper.toMovies
import com.example.tmdb.data.remote.MoviesRemoteDataSource
import com.example.tmdb.data.remote.models.MoviesDto
import com.example.tmdb.domain.Category
import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.Result
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

    val moviesFlow = localDataSource.movies.map { it.map { it.toMovies(
        genre = localDataSource.getGenreForMovie(it.id),
        category = localDataSource.getCategoryForMovie(it.id)
    ) } }

    suspend fun bookMarkMovie(movieId:Int,bookMark: Boolean){
        localDataSource.bookMark(bookMark,movieId)

    }

    suspend fun getMovies():Result<Flow<List<Movies>>, DataErrors>{
        return if (localDataSource.emptyTable()) {
            dataSync(1,Category.NowPlaying)
            dataSync(1,Category.UpComing)
            dataSync(1, Category.TopRated)
            dataSync(1, Category.Popular)

            Result.Success(moviesFlow)

        } else {
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