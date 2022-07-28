package com.example.handballcourtmanager.db.matchesdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatchesDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMatch(match: Match)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllPlayers(players:List<Match>)

    @Delete
    suspend fun delete(match: Match)

    @Update
    suspend fun update(match: Match)

    @Query("SELECT * FROM matches WHERE id=:id")
    fun getMatch(id:Int):LiveData<Match>

    @Query("SELECT * FROM matches WHERE is_completed=0 ORDER BY match_type")
    fun getCurrentMatches(): LiveData<List<Match>>

    @Query("SELECT * FROM matches WHERE is_completed=1 ORDER BY match_type")
    fun getCompletedMatches(): LiveData<List<Match>>

    @Query("DELETE FROM matches WHERE is_completed=0")
    suspend fun deleteCurrentMatches()

    @Query("DELETE FROM matches WHERE is_completed=1")
    suspend fun deleteCompletedMatches()



}