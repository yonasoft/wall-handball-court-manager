package com.yonasoft.handballcourtmanager.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchesDao
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchesDatabase
import java.util.*


//Repository to retrieve data database
class MatchesRepository(context: Context) {
    private var matchesDao: MatchesDao?= MatchesDatabase.getInstance(context).matchesDao

    companion object{
        //Create instance of repository when application starts
        private var INSTANCE: MatchesRepository?=null
        //Initialize repository
        fun initialize(context: Context){
            if(INSTANCE ==null){
                INSTANCE = MatchesRepository(context)
            }
        }
        //Get repository
        fun get(): MatchesRepository {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }
    //Add match
    suspend fun addMatch(match: Match){
        matchesDao!!.addMatch(match)
    }
    //Add multiple matches from a list
    suspend fun addAllMatch(matches: List<Match>){
        matchesDao!!.addAllMatches(matches)
    }
    //Remove a match
    suspend fun removeMatch(match: Match){
        matchesDao!!.delete(match)
    }
    //Remove all active matches
    suspend fun removeAllCurrentMatches(){
        matchesDao!!.deleteCurrentMatches()
    }
    //Update match
    suspend fun updateMatch(match:Match){
        matchesDao!!.update(match)
    }
    //Removes all completed matches
    suspend fun removeAllCompletedMatches(){
        matchesDao!!.deleteCompletedMatches()
    }
    //Gets match based on id
    fun getMatch(id: UUID):LiveData<Match>{
        return matchesDao!!.getMatch(id)
    }
    //Gets all active matches
    fun getAllCurrentMatches():LiveData<List<Match>>{
        return matchesDao!!.getCurrentMatches()
    }
    //Gets all completed matches
    fun getAllCompletedMatches():LiveData<List<Match>>{
        return matchesDao!!.getCompletedMatches()
    }

}