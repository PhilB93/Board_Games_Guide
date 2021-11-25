package com.example.boardgamesguide.ui.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.R
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.use_case.RandomGamesUseCase
import com.example.boardgamesguide.domain.use_case.TopGamesUseCase
import com.example.boardgamesguide.prefsstore.PrefsStore
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MainViewModel @Inject constructor(
    private val topGamesUseCase: TopGamesUseCase,
    private val randomGamesUseCase: RandomGamesUseCase,
    private val prefsStore: PrefsStore
) : ViewModel() {


    private val _randomGame: MutableStateFlow<NetworkResult<GameItems>> = MutableStateFlow(
        NetworkResult.LoadingState
    )
    val randomGame = _randomGame.asStateFlow()

    val darkThemeEnabled = prefsStore.isNightMode()


    fun topGames() = topGamesUseCase()

    init {
        randomGames()
    }
    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.toggleNightMode()
        }
    }
    fun randomGames() {
        viewModelScope.launch {
            randomGamesUseCase(
            ).collect {
                _randomGame.value = it
            }
        }
    }


}


