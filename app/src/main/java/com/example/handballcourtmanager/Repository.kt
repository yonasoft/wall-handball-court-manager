package com.example.handballcourtmanager

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.handballcourtmanager.db.Player
import com.example.handballcourtmanager.db.PlayerDao
import com.example.handballcourtmanager.db.PlayerDatabase

//Repository to retrieve data database
class Repository(context: Context) {
    private var playerDao:PlayerDao?=PlayerDatabase.getInstance(context).playerDao


    companion object{
        //Create instance of repository when application starts
        private var INSTANCE: Repository? =null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = Repository(context)
            }
        }

        fun get():Repository{
            return INSTANCE?:
            throw IllegalStateException("Repository must be initialized")
        }
    }

    //Add player to database
    suspend fun addPlayer(player:Player){
        playerDao!!.addPlayer(player)
    }

    //Removes player from queue
    suspend fun deletePlayer(player:Player){
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



}