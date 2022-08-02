package com.example.handballcourtmanager.db.playersdb


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlayer(player: Player)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllPlayers(players:List<Player>)

    @Delete
    suspend fun delete(player: Player)

    @Update
    suspend fun update(player: Player)

    @Query("SELECT * FROM players_in_queue WHERE is_winner=0 ORDER BY id ASC")
    fun getRegularPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM players_in_queue WHERE is_winner=1 ORDER BY id ASC")
    fun getWinnerPlayers():LiveData<List<Player>>

    @Query("DELETE FROM players_in_queue")
    suspend fun deleteAll()

    @Query("DELETE FROM players_in_queue WHERE is_winner=0")
    suspend fun deleteRegularPlayers()

    @Query("DELETE FROM players_in_queue WHERE is_winner=1")
    suspend fun deleteWinnerPlayers()


}