package com.example.handballcourtmanager.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.handballcourtmanager.db.matchesdb.Match
import com.example.handballcourtmanager.db.matchesdb.MatchesDao
import com.example.handballcourtmanager.db.matchesdb.MatchesDatabase
import java.util.*


//Repository to retrieve data database
class MatchesRepository(context: Context) {
    private var matchesDao: MatchesDao?= MatchesDatabase.getInstance(context).matchesDao

    companion object{
        //Create instance of repository when application starts
        private var INSTANCE: MatchesRepository? =null

        fun initialize(context: Context){
            if(INSTANCE ==null){
                INSTANCE = MatchesRepository(context)
            }
        }

        fun get(): MatchesRepository {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }

    suspend fun addMatch(match: Match){
        matchesDao!!.addMatch(match)
    }

    suspend fun addAllMatch(matches: List<Match>){
        matchesDao!!.addAllMatches(matches)
    }

    suspend fun removeMatch(match: Match){
        matchesDao!!.delete(match)
    }


    suspend fun removeAllCurrentMatches(){
        matchesDao!!.deleteCurrentMatches()
    }

    suspend fun updateMatch(match:Match){
        matchesDao!!.update(match)
    }

    suspend fun removeAllCompletedMatches(){
        matchesDao!!.deleteCompletedMatches()
    }

    fun getMatch(id: UUID):LiveData<Match>{
        return matchesDao!!.getMatch(id)
    }

    fun getAllCurrentMatches():LiveData<List<Match>>{
        return matchesDao!!.getCurrentMatches()
    }

    fun getAllCompletedMatches():LiveData<List<Match>>{
        return matchesDao!!.getCompletedMatches()
    }

}