package com.example.boardgamesguide.feature_boardgames.presentation.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.feature_boardgames.domain.model.GameItems
import com.example.boardgamesguide.feature_boardgames.domain.use_case.SearchBoardGamesUseCase
import com.example.boardgamesguide.feature_boardgames.domain.prefsstore.PrefsStore
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BoardGamesViewModel @Inject constructor(
    private val searchBoardGamesUseCase: SearchBoardGamesUseCase,
    private val prefsStore: PrefsStore,
) : ViewModel() {

    val darkThemeEnabled = prefsStore.isNightMode()

    private val _searchGames: MutableStateFlow<NetworkResult<GameItems>> = MutableStateFlow(
        NetworkResult.EmptyState
    )
    val searchGames = _searchGames.asStateFlow()

    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.toggleNightMode()
        }
    }

    fun searchGames(name:String) {
        viewModelScope.launch {
            searchBoardGamesUseCase(
                name = name
            ).collect {
                _searchGames.value = it
            }
        }
    }
}


