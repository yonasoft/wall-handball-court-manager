package com.example.handballcourtmanager.db


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlayer(player:Player)

    @Delete
    suspend fun delete(player: Player)

    @Update
    suspend fun update(player: Player)

    @Query("SELECT * FROM players_in_queue WHERE is_winner=0 ORDER BY id ASC")
    fun getRegularPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM players_in_queue WHERE is_winner=1 ORDER BY id ASC")
    fun getWinnerPlayers():LiveData<List<Player>>

}