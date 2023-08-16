package com.yonasoft.handballcourtmanager.repositories

import androidx.lifecycle.LiveData
import com.yonasoft.handballcourtmanager.db.playersdb.Player
import com.yonasoft.handballcourtmanager.db.playersdb.PlayerDao
import javax.inject.Inject

/**
 * Repository to handle database interactions related to players.
 */
class PlayersRepository @Inject constructor(private val playerDao: PlayerDao) {

    // CRUD operations for the player

    /** Adds a player to the database. */
    suspend fun addPlayer(player: Player) = playerDao.addPlayer(player)

    /** Adds multiple players to the database. */
    suspend fun addAllPlayers(players: List<Player>) = playerDao.addAllPlayers(players)

    /** Removes a specific player from the database. */
    suspend fun deletePlayer(player: Player) = playerDao.delete(player)

    /** Updates the details of a specific player in the database. */
    suspend fun updatePlayer(player: Player) = playerDao.update(player)

    // Special operations related to the game queues

    /** Removes all players in the regular queue from the database. */
    suspend fun deleteRegularPlayers() = playerDao.deleteRegularPlayers()

    /** Removes all players in the winner's queue from the database. */
    suspend fun deleteWinnerPlayers() = playerDao.deleteWinnerPlayers()

    /** Removes all players from the database. */
    suspend fun deleteAllPlayers() = playerDao.deleteAll()

    // Retrieval operations

    /** Retrieves all players in the regular queue from the database. */
    fun getRegularRoster(): LiveData<List<Player>> = playerDao.getRegularPlayers()

    /** Retrieves all players in the winner's queue from the database. */
    fun getWinnersRoster(): LiveData<List<Player>> = playerDao.getWinnerPlayers()
}
