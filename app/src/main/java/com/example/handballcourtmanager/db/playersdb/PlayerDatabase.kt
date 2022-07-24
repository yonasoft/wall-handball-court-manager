package com.example.handballcourtmanager.db.playersdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Player::class], version = 1, exportSchema = true)
abstract class PlayerDatabase: RoomDatabase() {

    abstract val playerDao: PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: PlayerDatabase? = null

        fun getInstance(context: Context): PlayerDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlayerDatabase::class.java,
                        "player_database"
                    ).build()
                    INSTANCE = instance

                }
                return instance

            }

        }

    }
}