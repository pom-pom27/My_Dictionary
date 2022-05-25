package com.example.mydictionary.feature_dictionary.data.remote.dto

import com.example.mydictionary.feature_dictionary.data.local.entities.WordInfoEntities

data class WordInfoDto(
    val license: LicenseDto,
    val meanings: List<MeaningDto>,
    val phonetic: String?,
    val phonetics: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
) {
//    fun toWordInfo(): WordInfo =
    //        WordInfo(
    //            phonetic = phonetic,
    //            word = word,
    //            meanings = meanings.map { it.toMeaning() },
    //            sourceUrls = sourceUrls
    //        )

    fun toWordInfoEntity(): WordInfoEntities = WordInfoEntities(
        meanings = meanings.map { it.toMeaning() },
        phonetic = phonetic,
        sourceUrls = sourceUrls,
        word = word
    )
}
