package com.example.tmdb.data.remote

import android.util.Log
import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun<reified D> responseToResult(
    response: HttpResponse
): Result<D, DataErrors.RemoteError> {
    val tag = "Response_toResult"
    return when(response.status.value){
        in 200..299 -> {
            try {
                Log.d(tag,response.body())
                Result.Success(response.body())
            } catch (error: NoTransformationFoundException){
                Log.d(tag,"Serialization Error: $error")
                Result.Error(DataErrors.RemoteError.SERIALIZATION)
            }
        }
         401 -> Result.Error(DataErrors.RemoteError.UNAUTHORISED)

        else ->{
            Log.d(tag,"Unknown, code: ${response.status.value} status : ${response.status}" )
            Result.Error(DataErrors.RemoteError.UNKNOWN)
        }
    }

}