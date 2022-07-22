package com.example.handballcourtmanager

import android.app.Application

class HandballApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}