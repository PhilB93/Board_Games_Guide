package com.example.boardgamesguide.feature_boardgames.presentation.main


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.feature_boardgames.domain.prefsstore.PrefsStore
import com.example.boardgamesguide.feature_boardgames.domain.use_case.SearchBoardGamesUseCase
import com.example.boardgamesguide.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BoardGamesViewModel @Inject constructor(
    private val searchBoardGamesUseCase: SearchBoardGamesUseCase,
    private val prefsStore: PrefsStore
) : ViewModel() {

    /** Light/Dark Mode */
    val darkThemeEnabled = prefsStore.isNightMode()

    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.toggleNightMode()
        }
    }
    /** Search games by query */
    private val _searchQuery = MutableStateFlow("")
   // val searchQuery = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow(GameListState())
    val state =_state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(100L)
            searchBoardGamesUseCase(query)
                .onEach { result ->
                    Log.i("123", result.toString())
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                games = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                games = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                isLoading = true,
                                games = result.data ?: emptyList()

                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }

}




