package com.example.mydictionary.feature_dictionary.domain.models

data class WordInfo(
    val meanings: List<Meaning>,
    val phonetic: String?,
    val sourceUrls: List<String>,
    val word: String
)
