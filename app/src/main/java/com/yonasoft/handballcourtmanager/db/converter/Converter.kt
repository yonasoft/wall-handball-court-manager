package com.yonasoft.handballcourtmanager.db.converter

import androidx.room.TypeConverter
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType
import com.yonasoft.handballcourtmanager.db.playersdb.Player
import java.util.*

class Converter {

    @TypeConverter
    fun fromMatchType(matchType: MatchType): String{
        return matchType.name
    }

    @TypeConverter
    fun toMatchType(matchType: String): MatchType {
        return MatchType.valueOf(matchType)
    }

}