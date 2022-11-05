package com.yonasoft.handballcourtmanager.fragments.details.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.repositories.MatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(private val matchesRepository: MatchesRepository, savedStateHandle: SavedStateHandle) : ViewModel() {

    var match:LiveData<Match>
    init {
        match = matchesRepository.getMatch(savedStateHandle["matchId"]!!)
    }


    //Add point based on team as parameter represented as string
    fun addPoints(team:String){
        when(team){
            "t1" -> match.value!!.scores[1] = match.value!!.scores[1]!! + 1
            "t2" -> match.value!!.scores[2] = match.value!!.scores[2]!! + 1
            "t3" -> match.value!!.scores[3] = match.value!!.scores[3]!! + 1
        }
        //Updates match after change
        updateMatch()
    }
    //Deducts point based on team as parameter represented as string
    fun deductPoints(team:String){
        when(team){
            "t1" -> if(match.value!!.scores[1]!!>-1) match.value!!.scores[1]!! - 1
            "t2" -> if(match.value!!.scores[2]!!>-1) match.value!!.scores[2]!! - 1
            "t3" -> if(match.value!!.scores[3]!!>-1) match.value!!.scores[3]!! - 1
        }
        //Updates match after change
        updateMatch()
    }
    //Sets the isCompleted to true to end the match
    fun completeMatch(){
        match.value!!.isCompleted=true
        updateMatch()
    }
    //Updates match after court number change
    //Was able to use two data binding for this property so need to change the matches value here
    fun updateCourtNum(courtNum:String){
        if(courtNum!=""){
            updateMatch()
        }
    }
    //Updates the mach
    fun updateMatch(match: Match= this.match.value!!) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                matchesRepository.updateMatch(match)
            }
        }

    }

}


