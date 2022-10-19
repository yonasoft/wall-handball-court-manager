package com.yonasoft.handballcourtmanager.di

import android.content.Context
import androidx.room.Room
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchesDao
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchesDatabase
import com.yonasoft.handballcourtmanager.db.playersdb.PlayerDao
import com.yonasoft.handballcourtmanager.db.playersdb.PlayerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideMatchesDao(matchesDatabase: MatchesDatabase): MatchesDao = matchesDatabase.matchesDao

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): MatchesDatabase {
        return Room.databaseBuilder(context,
            MatchesDatabase::class.java,
            "matches"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePlayerDao(playerDatabase: PlayerDatabase): PlayerDao = playerDatabase.playerDao

    @Singleton
    @Provides
    fun providePlayerDatabase(@ApplicationContext context: Context): PlayerDatabase {
        return Room.databaseBuilder(context,
            PlayerDatabase::class.java,
            "players"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}