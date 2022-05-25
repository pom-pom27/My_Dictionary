package com.example.mydictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mydictionary.feature_dictionary.presentation.WordInfoItem
import com.example.mydictionary.feature_dictionary.presentation.WordInfoViewModel
import com.example.mydictionary.ui.theme.DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryTheme {
                val viewModel: WordInfoViewModel = hiltViewModel()
                val state = viewModel.state
                val scaffoldState = rememberScaffoldState()

                //? for listen event flow in view model
                LaunchedEffect(key1 = true) {
                    viewModel.evenFlow.collectLatest { event ->
                        when (event) {
                            is WordInfoViewModel.UiEvent.showSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                            }
                        }

                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            TextField(
                                value = viewModel.searchQuery.value,
                                onValueChange = viewModel::onSearch,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Search...") }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            if (state.value.wordInfoItems.isEmpty()) {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                                    Text(text = "Start search the word...")
                                }
                            }
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(state.value.wordInfoItems.size) { idx ->
                                    val wordInfo = state.value.wordInfoItems[idx]
                                    if (idx > 0) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    WordInfoItem(wordInfo = wordInfo)

                                    if (idx < state.value.wordInfoItems.size - 1) {
                                        Divider()
                                    }
                                }
                            }
                        }
                        if (state.value.isLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
