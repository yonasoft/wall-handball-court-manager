package com.yonasoft.handballcourtmanager.fragments.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.repositories.MatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var match: LiveData<Match> = matchesRepository.getMatch(savedStateHandle["matchId"]!!)

    private fun updateScoreForTeam(team: String, modifier: Int) {
        val updatedMatch = match.value?.copy()
        updatedMatch?.let {
            val index = when (team) {
                "t1" -> 1
                "t2" -> 2
                "t3" -> 3
                else -> return
            }
            it.scores[index] = (it.scores[index] ?: 0) + modifier
            updateMatch(it)
        }
    }

    fun addPoints(team: String) = updateScoreForTeam(team, 1)

    fun deductPoints(team: String) = updateScoreForTeam(team, -1)

    fun completeMatch() {
        val updatedMatch = match.value?.copy(isCompleted = true)
        updatedMatch?.let { updateMatch(it) }
    }

    fun updateCourtNum(courtNum: String) {
        if (courtNum.isNotBlank()) {
            updateMatch()
        }
    }

    fun updateMatch(match: Match = this.match.value!!) {
        viewModelScope.launch(Dispatchers.IO) {
            matchesRepository.updateMatch(match)
        }
    }
}



