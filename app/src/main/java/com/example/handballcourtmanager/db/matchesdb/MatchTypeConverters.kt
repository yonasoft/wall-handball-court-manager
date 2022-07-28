package com.example.handballcourtmanager.db.matchesdb

import androidx.room.TypeConverter
import com.example.handballcourtmanager.db.playersdb.Player

class MatchTypeConverters {
    @TypeConverter
    fun fromPlayer(player:Player):String{
        return player.name
    }
    @TypeConverter
    fun toPlayer(name:String):Player{
        return Player(0,name,false)
    }
}