package com.example.handballcourtmanager

import android.app.Application

//Application context for this app
class HandballApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}