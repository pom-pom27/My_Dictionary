package com.example.mydictionary.feature_dictionary.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mydictionary.feature_dictionary.domain.models.Meaning
import com.example.mydictionary.feature_dictionary.domain.models.WordInfo

@Entity
data class WordInfoEntities(
    val meanings: List<Meaning>,
    val phonetic: String?,
    val sourceUrls: List<String>,
    val word: String,
    @PrimaryKey val id: Int? = null
) {

    fun toWordInfo(): WordInfo =
        WordInfo(meanings = meanings, phonetic = phonetic, sourceUrls = sourceUrls, word = word)

}
