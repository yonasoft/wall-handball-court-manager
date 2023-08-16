package com.yonasoft.handballcourtmanager.repositories

import androidx.lifecycle.LiveData
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchesDao
import java.util.UUID
import javax.inject.Inject

class MatchesRepository @Inject constructor(private val matchesDao: MatchesDao) {

    suspend fun addMatch(match: Match) = matchesDao.addMatch(match)

    suspend fun addAllMatches(matches: List<Match>) = matchesDao.addAllMatches(matches)

    suspend fun removeMatch(match: Match) = matchesDao.delete(match)

    suspend fun removeAllCurrentMatches() = matchesDao.deleteCurrentMatches()

    suspend fun updateMatch(match: Match) = matchesDao.update(match)

    suspend fun removeAllCompletedMatches() = matchesDao.deleteCompletedMatches()

    fun getMatch(id: UUID): LiveData<Match> = matchesDao.getMatch(id)

    fun getAllCurrentMatches(): LiveData<List<Match>> = matchesDao.getCurrentMatches()

    fun getAllCompletedMatches(): LiveData<List<Match>> = matchesDao.getCompletedMatches()
}
