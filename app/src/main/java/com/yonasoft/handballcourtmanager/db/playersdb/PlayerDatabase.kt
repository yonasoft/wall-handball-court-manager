package com.yonasoft.handballcourtmanager.db.playersdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Player::class], version = 1, exportSchema = true)
abstract class PlayerDatabase: RoomDatabase() {
    abstract val playerDao: PlayerDao
}