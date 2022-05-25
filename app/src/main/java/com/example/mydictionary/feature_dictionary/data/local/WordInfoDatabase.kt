package com.example.mydictionary.feature_dictionary.data.local.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mydictionary.feature_dictionary.data.local.Converters
import com.example.mydictionary.feature_dictionary.data.local.WordInfoDao

@Database(entities = [WordInfoEntities::class], version = 1)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase : RoomDatabase() {

    abstract val dao: WordInfoDao
}
