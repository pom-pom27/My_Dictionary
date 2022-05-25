package com.example.mydictionary.feature_dictionary.data.remote.dto

import com.example.mydictionary.feature_dictionary.domain.models.Meaning

data class MeaningDto(
    val antonyms: List<String>,
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
    val synonyms: List<String>
) {
    fun toMeaning(): Meaning =
        Meaning(definitions = definitions.map { it.toDefinition() }, partOfSpeech = partOfSpeech)
}
