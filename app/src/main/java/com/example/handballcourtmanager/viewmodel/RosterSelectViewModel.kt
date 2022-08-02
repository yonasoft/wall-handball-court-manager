package com.example.handballcourtmanager.viewmodel

import androidx.lifecycle.*
import com.example.handballcourtmanager.repositories.PlayersRepository
import com.example.handballcourtmanager.db.playersdb.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RosterSelectViewModel : ViewModel() {

    val nameToAdd = MutableLiveData("")

    var regularQueue: LiveData<List<Player>> = PlayersRepository.get().getRegularRoster()
    var winnerQueue: LiveData<List<Player>> = PlayersRepository.get().getWinnersRoster()


    fun deletePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PlayersRepository.get().deletePlayer(player)
            }
        }
    }
}

class RosterSelectViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RosterSelectViewModel() as T
    }
}