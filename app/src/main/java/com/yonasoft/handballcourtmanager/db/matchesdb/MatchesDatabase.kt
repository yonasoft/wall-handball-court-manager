package com.yonasoft.handballcourtmanager.db.matchesdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yonasoft.handballcourtmanager.db.converter.Converter


@Database(entities = [Match::class], version = 3, exportSchema = true)
@TypeConverters(Converter::class)
abstract class MatchesDatabase: RoomDatabase() {

    abstract val matchesDao: MatchesDao

    companion object {
        @Volatile
        private var INSTANCE: MatchesDatabase? = null

        fun getInstance(context: Context): MatchesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MatchesDatabase::class.java,
                        "matches_database"
                    ).build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}