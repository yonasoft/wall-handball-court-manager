package com.yonasoft.handballcourtmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonasoft.handballcourtmanager.repositories.MatchesRepository
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MatchesViewModel:ViewModel() {
    //Values for number of matches to add in add match dialog
    val numOfSinglesToAdd = MutableLiveData(0)
    val numOfDoublesToAdd = MutableLiveData(0)
    val numOfTrianglesToAdd = MutableLiveData(0)
    //Current matches from database
    val matchesList: LiveData<List<Match>> = MatchesRepository.get().getAllCurrentMatches()
    val resultsList: LiveData<List<Match>> = MatchesRepository.get().getAllCompletedMatches()
    //Adds matches based on parameters passed
    fun addMatches(
        singles: Int = numOfSinglesToAdd.value!!,
        doubles: Int = numOfDoublesToAdd.value!!,
        triangles: Int = numOfTrianglesToAdd.value!!
    ) {
        for (i in 1..singles) {
            createMatch(MatchTypes.SINGLES)
        }
        for (i in 1..doubles) {
            createMatch(MatchTypes.DOUBLES)
        }
        for (i in 1..triangles) {
            createMatch(MatchTypes.TRIANGLE)
        }
    }
    //Adds a match
    fun addMatch(match: Match) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().addMatch(match)
            }
        }
    }
    //Adds matches as a list
    fun addMatches(matches: List<Match>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().addAllMatch(matches)
            }
        }
    }
    //Creates and adds a new match
    private fun createMatch(
        matchType: String,
        ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().addMatch(
                    Match(UUID.randomUUID(),matchType, courtNumber = "N/A")
                )
            }
        }
    }

    //Removes a match
    fun removeMatch(match:Match){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().removeMatch(match)
            }
        }
    }
    //Clears all active matches
    fun removeAllCurrentMatches(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().removeAllCurrentMatches()
            }
        }
    }
    //Clears all completed matches
    fun removeAllResults(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().removeAllCompletedMatches()
            }
        }
    }
}