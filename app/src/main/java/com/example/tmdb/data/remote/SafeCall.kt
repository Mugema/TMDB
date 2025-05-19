package com.example.tmdb.data.remote

import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline  fun <reified D> safeCall(
    call : () -> HttpResponse
): Result<D, DataErrors.RemoteError>{
    val response = try {
        call()
    } catch (error: UnresolvedAddressException){
        return Result.Error(DataErrors.RemoteError.NO_INTERNET)
    }catch (error: SerializationException) {
        return Result.Error(DataErrors.RemoteError.SERIALIZATION)
    } catch (error: UnknownError) {
        coroutineContext.ensureActive()
        return Result.Error(DataErrors.RemoteError.UNKNOWN)
    }
    return responseToResult(response)

}