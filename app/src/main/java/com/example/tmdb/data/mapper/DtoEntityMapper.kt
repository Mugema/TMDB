package com.example.tmdb.data.mapper

import com.example.tmdb.data.local.MGenreEntity
import com.example.tmdb.data.local.MovieMGenreCrossRef
import com.example.tmdb.data.local.MoviesEntity
import com.example.tmdb.data.remote.MovieGenreDto
import com.example.tmdb.data.remote.NowPlayingMoviesDto
import com.example.tmdb.data.remote.PopularMoviesDto
import com.example.tmdb.data.remote.TopRatedDto
import com.example.tmdb.data.remote.UpComingDto

fun NowPlayingMoviesDto.toMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path,
            posterPath = results[index].poster_path,
            title = results[index].title,
            originalTitle = results[index].original_title,
            originalLanguage = results[index].original_language,
            video = results[index].video,
            overview = results[index].overview,
            releaseDate = results[index].release_date,
            voteAverage = results[index].vote_average,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}

fun UpComingDto.toMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path,
            posterPath = results[index].poster_path,
            title = results[index].title,
            originalTitle = results[index].original_title,
            originalLanguage = results[index].original_language,
            video = results[index].video,
            overview = results[index].overview,
            releaseDate = results[index].release_date,
            voteAverage = results[index].vote_average,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}

fun TopRatedDto.toMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path,
            posterPath = results[index].poster_path,
            title = results[index].title,
            originalTitle = results[index].original_title,
            originalLanguage = results[index].original_language,
            video = results[index].video,
            overview = results[index].overview,
            releaseDate = results[index].release_date,
            voteAverage = results[index].vote_average,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}

fun PopularMoviesDto.toMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path,
            posterPath = results[index].poster_path,
            title = results[index].title,
            originalTitle = results[index].original_title,
            originalLanguage = results[index].original_language,
            video = results[index].video,
            overview = results[index].overview,
            releaseDate = results[index].release_date,
            voteAverage = results[index].vote_average,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}

fun MovieGenreDto.toMGenre(index: Int):MGenreEntity{
    return MGenreEntity(
        genreId = genres[index].id,
        genre = genres[index].name
    )
}

fun NowPlayingMoviesDto.toMovieGenreCrossRef(index: Int):List<MovieMGenreCrossRef>{
    val list= listOf<MovieMGenreCrossRef>()
    for (i in results[index].genre_ids){
        list.plus(
            MovieMGenreCrossRef(
                genreId = i,
                id = results[index].id
            )
        )
    }

    return list
}

fun UpComingDto.toMovieGenreCrossRef(index: Int):List<MovieMGenreCrossRef>{
    val list= listOf<MovieMGenreCrossRef>()
    for (i in results[index].genre_ids){
        list.plus(
            MovieMGenreCrossRef(
                genreId = i,
                id = results[index].id
            )
        )
    }

    return list
}

fun TopRatedDto.toMovieGenreCrossRef(index: Int):List<MovieMGenreCrossRef>{
    val list= listOf<MovieMGenreCrossRef>()
    for (i in results[index].genre_ids){
        list.plus(
            MovieMGenreCrossRef(
                genreId = i,
                id = results[index].id
            )
        )
    }

    return list
}

fun PopularMoviesDto.toMovieGenreCrossRef(index: Int):List<MovieMGenreCrossRef>{
    val list= listOf<MovieMGenreCrossRef>()
    for (i in results[index].genre_ids){
        list.plus(
            MovieMGenreCrossRef(
                genreId = i,
                id = results[index].id
            )
        )
    }

    return list
}