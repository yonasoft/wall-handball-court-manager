package com.example.handballcourtmanager

import android.app.Application
import com.example.handballcourtmanager.repositories.MatchesRepository
import com.example.handballcourtmanager.repositories.PlayersRepository

//Application context
class HandballApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        //Initializes repositories
        PlayersRepository.initialize(this)
        MatchesRepository.initialize(this)
    }
}