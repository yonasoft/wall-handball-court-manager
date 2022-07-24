package com.example.handballcourtmanager

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.handballcourtmanager.db.playersdb.Player
import com.example.handballcourtmanager.db.playersdb.PlayerDao
import com.example.handballcourtmanager.db.playersdb.PlayerDatabase

//Repository to retrieve data database
class PlayersRepository(context: Context) {
    private var playerDao: PlayerDao?= PlayerDatabase.getInstance(context).playerDao


    companion object{
        //Create instance of repository when application starts
        private var INSTANCE: PlayersRepository? =null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = PlayersRepository(context)
            }
        }

        fun get():PlayersRepository{
            return INSTANCE?:
            throw IllegalStateException("Repository must be initialized")
        }
    }

    //Add player to database
    suspend fun addPlayer(player: Player){
        playerDao!!.addPlayer(player)
    }

    //Removes player from queue
    suspend fun deletePlayer(player: Player){
        playerDao!!.delete(player)
    }

    //Update the player in the database
    suspend fun updatePlayer(player: Player){
        playerDao!!.update(player)
    }

    //Get the normal queue list from the database
    fun getRegularRoster():LiveData<List<Player>>{
        return playerDao!!.getRegularPlayers()
    }

    //Get the winner queue list from the database
    fun getWinnersRoster(): LiveData<List<Player>>{
        return playerDao!!.getWinnerPlayers()
    }

    suspend fun deleteAllPlayers(){
        playerDao!!.deleteAll()
    }

    suspend fun deleteRegularPlayers(){
        playerDao!!.deleteRegularPlayers()
    }

    suspend fun deleteWinnerPlayers(){
        playerDao!!.deleteWinnerPlayers()
    }

    suspend fun addAllPlayers(players:List<Player>){
        playerDao!!.addAllPlayers(players)
    }


}