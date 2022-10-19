package com.yonasoft.handballcourtmanager

import android.app.Application
import com.yonasoft.handballcourtmanager.repositories.MatchesRepository
import com.yonasoft.handballcourtmanager.repositories.PlayersRepository
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HandballApplication:Application() {

}