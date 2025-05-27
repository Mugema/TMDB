package com.example.tmdb.domain

sealed interface DataErrors:Error {
    enum class RemoteError:DataErrors{
        NO_INTERNET,
        SERIALIZATION,
        UNKNOWN,
        UNAUTHORISED
    }
    enum class LocalError:DataErrors{
        NOTFOUND,
    }
}

interface Error