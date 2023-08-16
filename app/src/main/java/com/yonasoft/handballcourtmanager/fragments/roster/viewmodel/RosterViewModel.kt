package com.yonasoft.handballcourtmanager.fragments.roster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonasoft.handballcourtmanager.db.playersdb.Player
import com.yonasoft.handballcourtmanager.repositories.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RosterViewModel @Inject constructor(private val playersRepository: PlayersRepository) : ViewModel() {

    val nameToAdd = MutableLiveData<String>()
    val regularQueue: LiveData<List<Player>> = playersRepository.getRegularRoster()
    val winnerQueue: LiveData<List<Player>> = playersRepository.getWinnersRoster()

    fun addPlayer(name: String? = nameToAdd.value) {
        name?.let {
            executeDatabaseOperation {
                playersRepository.addPlayer(Player(id = 0, name = it))
            }
        }
    }

    fun addPlayer(player: Player) {
        executeDatabaseOperation {
            playersRepository.addPlayer(player)
        }
    }

    fun addAllPlayers(players: List<Player>) {
        executeDatabaseOperation {
            playersRepository.addAllPlayers(players)
        }
    }

    fun addAllPlayers(playerNames: List<String>, areWinners: Boolean) {
        val players = playerNames.map { Player(id = 0, name = it, isWinner = areWinners) }
        addAllPlayers(players)
    }

    fun deletePlayer(player: Player) {
        executeDatabaseOperation {
            playersRepository.deletePlayer(player)
        }
    }

    fun deleteAllPlayers() {
        executeDatabaseOperation {
            playersRepository.deleteAllPlayers()
        }
    }

    fun deleteRegularPlayers() {
        executeDatabaseOperation {
            playersRepository.deleteRegularPlayers()
        }
    }

    fun deleteWinnerPlayers() {
        executeDatabaseOperation {
            playersRepository.deleteWinnerPlayers()
        }
    }

    private fun executeDatabaseOperation(operation: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                operation.invoke()
            }
        }
    }
}
