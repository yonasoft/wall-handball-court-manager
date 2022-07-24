package com.example.handballcourtmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handballcourtmanager.PlayersRepository
import com.example.handballcourtmanager.db.playersdb.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//ViewModel for the Roster List Fragment
class RosterViewModel : ViewModel() {


    val nameToAdd = MutableLiveData<String>()

    var regularQueue: LiveData<List<Player>> = PlayersRepository.get().getRegularRoster()
    var winnerQueue: LiveData<List<Player>> = PlayersRepository.get().getWinnersRoster()

    fun addPlayer(name: String = this.nameToAdd.value!!) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().addPlayer(Player(id = 0, name = name))
            }
        }

    }

    fun addPlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().addPlayer(player)
            }
        }

    }

    fun addAllPlayers(players: List<Player>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().addAllPlayers(players)
            }
        }

    }

    fun updatePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().updatePlayer(player)
            }
        }

    }

    fun deletePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().deletePlayer(player)
            }
        }
    }

    fun deleteAllPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().deleteAllPlayers()
            }
        }
    }

    fun deleteRegularPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().deleteRegularPlayers()
            }
        }
    }

    fun deleteWinnerPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().deleteWinnerPlayers()
            }
        }
    }


}