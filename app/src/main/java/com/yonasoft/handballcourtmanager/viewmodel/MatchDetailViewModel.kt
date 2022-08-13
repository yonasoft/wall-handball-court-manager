package com.yonasoft.handballcourtmanager.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yonasoft.handballcourtmanager.repositories.MatchesRepository
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MatchDetailViewModel(matchId: UUID) : ViewModel() {

    var match:LiveData<Match>

        init{
            //Initialized the match property from the database to the view model
            match=MatchesRepository.get().getMatch(matchId)
        }
    //Add point based on team as parameter represented as string
    fun addPoints(team:String){
        when(team){
            "t1" -> match.value!!.teamOneScore++
            "t2" -> match.value!!.teamTwoScore++
            "t3" -> match.value!!.teamThreeScore++
        }
        //Updates match after change
        updateMatch()
    }
    //Deducts point based on team as parameter represented as string
    fun deductPoints(team:String){
        when(team){
            "t1" -> if(match.value!!.teamOneScore>-1) match.value!!.teamOneScore--
            "t2" -> if(match.value!!.teamTwoScore>-1) match.value!!.teamTwoScore--
            "t3" -> if(match.value!!.teamThreeScore>-1) match.value!!.teamThreeScore--
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