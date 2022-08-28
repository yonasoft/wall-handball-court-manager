package com.yonasoft.handballcourtmanager.db.matchesdb

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromMatchType(matchType: MatchType): String{
        return matchType.name
    }

    @TypeConverter
    fun toMatchType(matchType: String):MatchType{
        return MatchType.valueOf(matchType)
    }

}