package com.example.handballcourtmanager.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.handballcourtmanager.repositories.MatchesRepository
import com.example.handballcourtmanager.db.matchesdb.Match
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MatchDetailViewModel(matchId: UUID) : ViewModel() {

    var match:LiveData<Match>?=null

        init{
            match=MatchesRepository.get().getMatch(matchId)
        }


    fun addPoints(team:String){
        when(team){
            "t1" -> match!!.value!!.teamOneScore++
            "t2" -> match!!.value!!.teamTwoScore++
            "t3" -> match!!.value!!.teamThreeScore++
        }

        updateMatch()
    }

    fun deductPoints(team:String){
        when(team){
            "t1" -> if(match!!.value!!.teamOneScore>-1) match!!.value!!.teamOneScore--
            "t2" -> if(match!!.value!!.teamTwoScore>-1) match!!.value!!.teamTwoScore--
            "t3" -> if(match!!.value!!.teamThreeScore>-1) match!!.value!!.teamThreeScore--
        }

        updateMatch()
    }

    fun completeMatch(){
        match!!.value!!.isCompleted=true
        updateMatch()
    }

    fun updateCourtNum(courtNum:String){
        if(courtNum!=""){
            updateMatch()
        }

    }

    fun updateMatch(match: Match= this.match!!.value!!) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MatchesRepository.get().updateMatch(match)
            }
        }

    }

}

class MatchDetailViewModelFactory(
    private val matchId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MatchDetailViewModel(matchId) as T
    }
}