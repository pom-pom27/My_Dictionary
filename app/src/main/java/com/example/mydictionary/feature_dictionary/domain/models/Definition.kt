package com.example.mydictionary.feature_dictionary.domain.models

data class Definition(
    val antonyms: List<String>,
    val definition: String,
    val synonyms: List<String>,
    val example: String?,
)
