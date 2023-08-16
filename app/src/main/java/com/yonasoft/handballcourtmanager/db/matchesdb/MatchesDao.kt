package com.yonasoft.handballcourtmanager.db.matchesdb

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface MatchesDao{

    @Insert
    suspend fun addMatch(match: Match)

    @Insert
    suspend fun addAllMatches(matches:List<Match>)

    @Delete
    suspend fun delete(match: Match)

    @Update
    suspend fun update(match: Match)

    @Query("SELECT * FROM matches WHERE id=:id")
    fun getMatch(id: UUID):LiveData<Match>

    @Query("SELECT * FROM matches WHERE is_completed=0 ORDER BY match_type")
    fun getCurrentMatches(): LiveData<List<Match>>

    @Query("SELECT * FROM matches WHERE is_completed=1 ORDER BY match_type")
    fun getCompletedMatches(): LiveData<List<Match>>

    @Query("DELETE FROM matches WHERE is_completed=0")
    suspend fun deleteCurrentMatches()

    @Query("DELETE FROM matches WHERE is_completed=1")
    suspend fun deleteCompletedMatches()

}