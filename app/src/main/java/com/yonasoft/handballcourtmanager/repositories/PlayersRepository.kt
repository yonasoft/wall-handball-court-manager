package com.yonasoft.handballcourtmanager.repositories

import androidx.lifecycle.LiveData
import com.yonasoft.handballcourtmanager.db.playersdb.Player
import com.yonasoft.handballcourtmanager.db.playersdb.PlayerDao
import javax.inject.Inject


class PlayersRepository @Inject constructor(private val playerDao: PlayerDao) {

    suspend fun addPlayer(player: Player) = playerDao.addPlayer(player)

    suspend fun addAllPlayers(players: List<Player>) = playerDao.addAllPlayers(players)

    suspend fun deletePlayer(player: Player) = playerDao.delete(player)

    suspend fun updatePlayer(player: Player) = playerDao.update(player)

    suspend fun deleteRegularPlayers() = playerDao.deleteRegularPlayers()

    suspend fun deleteWinnerPlayers() = playerDao.deleteWinnerPlayers()

    suspend fun deleteAllPlayers() = playerDao.deleteAll()

    fun getRegularRoster(): LiveData<List<Player>> = playerDao.getRegularPlayers()

    fun getWinnersRoster(): LiveData<List<Player>> = playerDao.getWinnerPlayers()
}
