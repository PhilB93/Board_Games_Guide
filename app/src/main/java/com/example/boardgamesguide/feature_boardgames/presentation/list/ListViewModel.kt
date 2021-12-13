package com.example.boardgamesguide.feature_boardgames.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.feature_boardgames.domain.use_case.TopBoardGamesUseCase
import com.example.boardgamesguide.feature_boardgames.presentation.search.GameListState
import com.example.boardgamesguide.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
private val topBoardGamesUseCase: TopBoardGamesUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(GameListState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun getBoardGames()  = viewModelScope.launch {
        topBoardGamesUseCase()
                .collect { result ->
                    when (result) {
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
                            Log.i("123", result.data.toString())
                            _eventFlow.send(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                isLoading = true,
                                games = result.data ?: emptyList()
                            )
                        }
                    }
                    Log.i("123", result.data.toString())
                }
        }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}




