package com.example.mydictionary.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydictionary.feature_dictionary.data.local.entities.WordInfoEntities

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(infos: List<WordInfoEntities>)

    @Query("DELETE FROM wordinfoentities where word IN(:words)")
    suspend fun deleteWordInfos(words: List<String>)

    @Query("SELECT * FROM wordinfoentities WHERE word LIKE  '%' ||:word || '%'")
    suspend fun getWordInfo(word: String): List<WordInfoEntities>
}
