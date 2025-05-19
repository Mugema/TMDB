package com.example.tmdb.data.remote

import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun<reified D> responseToResult(
    response: HttpResponse
): Result<D, DataErrors.RemoteError> {
    return when(response.status.value){
        in 200..299 -> {
            try {
                Result.Success(response.body())
            } catch (error: NoTransformationFoundException){
                Result.Error(DataErrors.RemoteError.SERIALIZATION)
            }
        }
        else -> Result.Error(DataErrors.RemoteError.UNKNOWN)
    }
}