package com.example.handballcourtmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handballcourtmanager.Repository
import com.example.handballcourtmanager.db.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//ViewModel for the Roster List Fragment
class RosterViewModel : ViewModel() {


    val nameToAdd = MutableLiveData<String>()

    var regularQueue: LiveData<List<Player>> = Repository.get().getRegularRoster()
    var winnerQueue: LiveData<List<Player>> = Repository.get().getWinnersRoster()

    fun addPlayer(name: String = this.nameToAdd.value!!) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repository.get().addPlayer(Player(id = 0, name = name))
            }
        }

    }

    fun addPlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repository.get().addPlayer(player)
            }
        }

    }

    fun addAllPlayers(players: List<Player>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repository.get().addAllPlayers(players)
            }
        }

    }

    fun updatePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repository.get().updatePlayer(player)
            }
        }

    }

    fun deletePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repository.get().deletePlayer(player)
            }
        }
    }

    fun deleteAllPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repository.get().deleteAllPlayers()
            }
        }
    }

    fun deleteRegularPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repository.get().deleteRegularPlayers()
            }
        }
    }

    fun deleteWinnerPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repository.get().deleteWinnerPlayers()
            }
        }
    }


}