package com.example.mydictionary.feature_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydictionary.core.util.Constant
import com.example.mydictionary.core.util.Resource
import com.example.mydictionary.feature_dictionary.domain.use_case.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(private val getWordInfo: GetWordInfo) :
    ViewModel() {
    //?  create state of the screen
    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    //? create event flow to send one time event to the ui like showing snackBar etc
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val evenFlow = _eventFlow.asSharedFlow()

    //create job for doing debouncing
    private var searchJob: Job? = null

    //? create action event
    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(Constant.DELAY_SEARCH_TIME)

            getWordInfo(query).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            wordInfoItems = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value.copy(
                            wordInfoItems = result.data ?: emptyList(),
                            isLoading = false

                        )
                        _eventFlow.emit(UiEvent.showSnackbar(result.message ?: "Unknown error"))
                    }

                    is Resource.Loading -> {
                        _state.value.copy(
                            wordInfoItems = result.data ?: emptyList(),
                            isLoading = true

                        )
                    }
                }
            }.launchIn(this)
        }

    }

    sealed class UiEvent {
        data class showSnackbar(val message: String) : UiEvent()
    }
}
