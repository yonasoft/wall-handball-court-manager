package com.example.handballcourtmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handballcourtmanager.repositories.MatchesRepository
import com.example.handballcourtmanager.db.matchesdb.Match
import com.example.handballcourtmanager.db.matchesdb.MatchTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MatchesViewModel:ViewModel() {
    val numOfSinglesToAdd = MutableLiveData(0)
    val numOfDoublesToAdd = MutableLiveData(0)
    val numOfTrianglesToAdd = MutableLiveData(0)

    val matchesList: LiveData<List<Match>> = MatchesRepository.get().getAllCurrentMatches()
    val resultsList: LiveData<List<Match>> = MatchesRepository.get().getAllCompletedMatches()

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

    fun addMatch(match: Match) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().addMatch(match)
            }
        }

    }

    fun addMatches(matches: List<Match>) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().addAllMatch(matches)
            }
        }

    }

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

    fun updateMatch(match: Match) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().updateMatch(match)
            }
        }

    }

    fun removeMatch(match:Match){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().removeMatch(match)
            }
        }

    }
    fun removeAllCurrentMatches(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().removeAllCurrentMatches()
            }
        }
    }
    fun removeAllResults(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().removeAllCompletedMatches()
            }
        }
    }

}