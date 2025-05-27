package com.example.tmdb.data.remote

import com.example.tmdb.data.remote.models.ActorDto
import com.example.tmdb.data.remote.models.MoviesDto
import com.example.tmdb.domain.Category
import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class APIServiceImp @Inject constructor(
    private val client: HttpClient
): ApiService {
    override suspend fun getMovies(category: Category, page: Int): Result<MoviesDto, DataErrors.RemoteError> {
        return when(category){
            Category.NowPlaying -> {
                safeCall<MoviesDto> {
                    client.get {
                        url(ApiService.BASE_URL + ApiService.NOW_PLAYING)
                        parameter("api_key", ApiService.API_KEY)
                        parameter("page",page)
                    }
                }
            }

            Category.UpComing -> {
                safeCall<MoviesDto> {
                    client.get {
                        url(ApiService.BASE_URL + ApiService.UPCOMING)
                        parameter("api_key", ApiService.API_KEY)
                        parameter("page",page)
                    }
                }
            }

            Category.TopRated -> {
                safeCall<MoviesDto> {
                    client.get {
                        url(ApiService.BASE_URL + ApiService.TOP_RATED)
                        parameter("api_key", ApiService.API_KEY)
                        parameter("page",page)
                    }
                }
            }

            Category.Popular -> {
                safeCall<MoviesDto> {
                    client.get {
                        url(ApiService.BASE_URL + ApiService.POPULAR)
                        parameter("api_key", ApiService.API_KEY)
                        parameter("page",page)
                    }
                }
            }

        }
    }

    override suspend fun searchForMovie(query: String): Result<MoviesDto, DataErrors.RemoteError> {
        return safeCall<MoviesDto> {
            client.get {
                url(ApiService.BASE_URL + ApiService.SEARCH_MOVIE)
                parameter("api_key", ApiService.API_KEY)
                parameter("query",query)
            }
        }
    }

    override suspend fun searchForPerson(query: String): Result<ActorDto, DataErrors.RemoteError> {
        return safeCall<ActorDto> {
            client.get {
                url(ApiService.BASE_URL + ApiService.SEARCH_PERSON)
                parameter("api_key", ApiService.API_KEY)
                parameter("query",query)
            }
        }
    }

}