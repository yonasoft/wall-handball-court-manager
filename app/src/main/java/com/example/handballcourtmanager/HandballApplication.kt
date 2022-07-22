package com.example.handballcourtmanager

import android.app.Application

//Application context
class HandballApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}