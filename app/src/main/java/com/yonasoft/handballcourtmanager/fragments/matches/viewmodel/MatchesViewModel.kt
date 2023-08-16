package com.yonasoft.handballcourtmanager.fragments.matches.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonasoft.handballcourtmanager.repositories.MatchesRepository
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.Pair
import java.util.*
import javax.inject.Inject

fun CoroutineScope.launchIO(block: suspend () -> Unit) {
    launch(Dispatchers.IO) { block() }
}

@HiltViewModel
class MatchesViewModel @Inject constructor(private val matchesRepository: MatchesRepository): ViewModel() {

    val numOfSinglesToAdd = MutableLiveData(0)
    val numOfDoublesToAdd = MutableLiveData(0)
    val numOfTrianglesToAdd = MutableLiveData(0)

    val matchesList: LiveData<List<Match>> = matchesRepository.getAllCurrentMatches()
    val resultsList: LiveData<List<Match>> = matchesRepository.getAllCompletedMatches()

    fun addMatches(
        singles: Int = numOfSinglesToAdd.value ?: 0,
        doubles: Int = numOfDoublesToAdd.value ?: 0,
        triangles: Int = numOfTrianglesToAdd.value ?: 0
    ) {
        listOf(
            Pair(singles, MatchType.SINGLES),
            Pair(doubles, MatchType.DOUBLES),
            Pair(triangles, MatchType.TRIANGLE)
        ).forEach { (count, type) ->
            repeat(count) { createMatch(type) }
        }
    }

    fun addMatch(match: Match) = viewModelScope.launchIO { matchesRepository.addMatch(match) }

    fun addMatches(matches: List<Match>) = viewModelScope.launchIO { matchesRepository.addAllMatch(matches) }

    private fun createMatch(matchType: MatchType) = viewModelScope.launchIO {
        matchesRepository.addMatch(Match(UUID.randomUUID(), matchType, courtNumber = "N/A"))
    }

    fun removeMatch(match: Match) = viewModelScope.launchIO { matchesRepository.removeMatch(match) }

    fun removeAllCurrentMatches() = viewModelScope.launchIO { matchesRepository.removeAllCurrentMatches() }

    fun removeAllResults() = viewModelScope.launchIO { matchesRepository.removeAllCompletedMatches() }

}