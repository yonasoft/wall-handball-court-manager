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
}