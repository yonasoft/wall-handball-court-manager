package com.example.handballcourtmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handballcourtmanager.MatchesRepository
import com.example.handballcourtmanager.db.matchesdb.Match
import com.example.handballcourtmanager.db.matchesdb.MatchTypes
import com.example.handballcourtmanager.db.playersdb.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchesViewModel:ViewModel() {
    val numOfSinglesToAdd = MutableLiveData<Int>(0)
    val numOfDoublesToAdd = MutableLiveData<Int>(0)
    val numOfTrianglesToAdd = MutableLiveData<Int>(0)

    val matchesList: LiveData<List<Match>> = MatchesRepository.get().getAllCurrentMatches()

    fun addMatches(
        singles: Int = this.numOfSinglesToAdd.value!!,
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

    private fun createMatch(
        matchType: String,

        ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().addMatch(Match(id = 0, matchType))
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




}