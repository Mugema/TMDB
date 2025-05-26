package com.example.tmdb.data.local.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Upsert
import com.example.tmdb.data.local.model.MoviesEntity
import com.example.tmdb.data.local.model.PersonalityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalityDao {
    @Upsert
    suspend fun addPersonality(personalityEntity: PersonalityEntity)

    @Query("Select * from MoviesEntity join PersonalityEntity on PersonalityEntity.id")
    fun getAllPersonalities(): Flow<List<PersonalityEntity>>

    @Query("Select * from PersonalityEntity where name like '%' ||:query|| '%' or " +
            "originalName like '%' || :query || '%' ")
    suspend fun getPersonalityBasedOnName(query: String): List<PersonalityEntity>

    @Query("Select * from MoviesEntity join PersonalityEntity on knownFor = MoviesEntity.id" +
            " where  PersonalityEntity.id = :id")
    suspend fun getMoviesForPersonality(id:Int): List<MoviesForPersonality>
}

data class MoviesForPersonality(
    val knownFor:Int,
    @Embedded val movies: MoviesEntity
)