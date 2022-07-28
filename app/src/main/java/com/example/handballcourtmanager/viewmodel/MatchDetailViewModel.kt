package com.example.handballcourtmanager.viewmodel

import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.handballcourtmanager.db.matchesdb.Match
import com.example.handballcourtmanager.db.playersdb.Player

class MatchDetailViewModel(var match: Match):ViewModel() {

    fun setPlayer(player: Player, textView:TextView){
        textView.text = player.name
    }




}