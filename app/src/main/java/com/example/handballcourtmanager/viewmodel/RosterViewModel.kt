package com.example.handballcourtmanager.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.handballcourtmanager.Repository
import com.example.handballcourtmanager.db.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//ViewModel for the Roster List Fragment
class RosterViewModel(): ViewModel() {


    val nameToAdd = MutableLiveData<String>()

    var regularQueue:LiveData<List<Player>> = Repository.get().getRegularRoster()

    fun addRegularQueue(name:String=this.nameToAdd.value!!){
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                Repository.get().addPlayer(Player(id = 0, name = name ))
            }
        }

    }



}