package com.yonasoft.handballcourtmanager.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.yonasoft.handballcourtmanager.db.playersdb.Player
import com.yonasoft.handballcourtmanager.db.playersdb.PlayerDao
import com.yonasoft.handballcourtmanager.db.playersdb.PlayerDatabase

//Repository to retrieve data database
class PlayersRepository(context: Context) {
    private var playerDao: PlayerDao = PlayerDatabase.getInstance(context).playerDao

    companion object{
        //Create instance of repository when application starts
        private var INSTANCE: PlayersRepository? =null
        //Initializes repository
        fun initialize(context: Context){
            if(INSTANCE ==null){
                INSTANCE = PlayersRepository(context)
            }
        }
        //Gets the repository
        fun get(): PlayersRepository {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }

    //Add player to database
    suspend fun addPlayer(player: Player){
        playerDao.addPlayer(player)
    }
    //Add a list of players to database
    suspend fun addAllPlayers(players:List<Player>){
        playerDao.addAllPlayers(players)
    }
    //Removes player from queue
    suspend fun deletePlayer(player: Player){
        playerDao.delete(player)
    }
    //Deletes all players in regular queue
    suspend fun deleteRegularPlayers(){
        playerDao.deleteRegularPlayers()
    }
    //Deletes all players in winner's queue
    suspend fun deleteWinnerPlayers(){
        playerDao.deleteWinnerPlayers()
    }
    //Deletes all queue
    suspend fun deleteAllPlayers(){
        playerDao.deleteAll()
    }
    //Update the player in the database
    suspend fun updatePlayer(player: Player){
        playerDao.update(player)
    }
    //Get the normal queue list from the database
    fun getRegularRoster():LiveData<List<Player>>{
        return playerDao.getRegularPlayers()
    }
    //Get the winner queue list from the database
    fun getWinnersRoster(): LiveData<List<Player>>{
        return playerDao.getWinnerPlayers()
    }







}