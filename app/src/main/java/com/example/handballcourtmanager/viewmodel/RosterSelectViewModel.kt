package com.example.handballcourtmanager.viewmodel

import androidx.lifecycle.*
import com.example.handballcourtmanager.repositories.PlayersRepository
import com.example.handballcourtmanager.db.playersdb.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RosterSelectViewModel : ViewModel() {

    val nameToAdd = MutableLiveData("")
    //Matches in regular queue
    var regularQueue: LiveData<List<Player>> = PlayersRepository.get().getRegularRoster()
    //Matches in winners queue
    var winnerQueue: LiveData<List<Player>> = PlayersRepository.get().getWinnersRoster()

    //Deletes player
    fun deletePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().deletePlayer(player)
            }
        }
    }
}


