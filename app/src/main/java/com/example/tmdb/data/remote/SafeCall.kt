package com.example.tmdb.data.remote

import android.util.Log
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
    val tag = "Safe_Call"
    val response = try {
        call()
    } catch (error: UnresolvedAddressException){
        Log.d(tag,"Unresolved")
        return Result.Error(DataErrors.RemoteError.NO_INTERNET)
    }catch (error: SerializationException) {
        Log.d(tag,"Serialization")
        return Result.Error(DataErrors.RemoteError.SERIALIZATION)
    } catch (error: UnknownError) {
        Log.d(tag,"Unknown")
        coroutineContext.ensureActive()
        return Result.Error(DataErrors.RemoteError.UNKNOWN)
    }
    Log.d(tag,"No Error")
    return responseToResult(response)

}