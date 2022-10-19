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

//ViewModel for the Roster List Fragment
@HiltViewModel
class RosterViewModel @Inject constructor(private val playersRepository: PlayersRepository) : ViewModel() {

    val nameToAdd = MutableLiveData<String>()
    //Matches in regular queue
    val regularQueue: LiveData<List<Player>> = playersRepository.getRegularRoster()
    //Matches in winners queue
    val winnerQueue: LiveData<List<Player>> = playersRepository.getWinnersRoster()

    //Add  new player with string
    fun addPlayer(name: String = this.nameToAdd.value!!) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playersRepository.addPlayer(Player(id = 0, name = name))
            }
        }
    }
    //Add player as Player class object
    fun addPlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playersRepository.addPlayer(player)
            }
        }
    }
    //Add multiple players from list
    fun addAllPlayers(players: List<Player>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playersRepository.addAllPlayers(players)
            }
        }

    }
    //Add multiple players from list to specific queue(isWinner/areWinner)
    fun addAllPlayers(players: List<String>, areWinners:Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val playersAsClass:MutableList<Player> = mutableListOf()
                for (player in players){
                    playersAsClass.add(Player(0,player,areWinners))
                }
                addAllPlayers(playersAsClass)
            }
        }
    }

    //Delete player from database
    fun deletePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playersRepository.deletePlayer(player)
            }
        }
    }
    //Deletes all players from database
    fun deleteAllPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playersRepository.deleteAllPlayers()
            }
        }
    }
    //Deletes all players in regular queue
    fun deleteRegularPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playersRepository.deleteRegularPlayers()
            }
        }
    }
    //Deletes all players from winners queue
    fun deleteWinnerPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playersRepository.deleteWinnerPlayers()
            }
        }
    }
}