package com.yonasoft.handballcourtmanager

import android.app.Application
import com.yonasoft.handballcourtmanager.repositories.MatchesRepository
import com.yonasoft.handballcourtmanager.repositories.PlayersRepository

//Application context
class HandballApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        //Initializes repositories
        PlayersRepository.initialize(this)
        MatchesRepository.initialize(this)
    }
}