package com.example.boardgamesguide.ui.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.R
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.use_case.RandomGamesUseCase
import com.example.boardgamesguide.domain.use_case.TopGamesUseCase
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MIN_PLAYERS = 1
private const val MAX_PLAYERS = 10
private const val MIN_PLAYTIME = 1
private const val MAX_PLAYTIME = 1200

@HiltViewModel
class MainViewModel @Inject constructor(
    private val topGamesUseCase: TopGamesUseCase,
    private val randomGamesUseCase: RandomGamesUseCase,
    private val pref: SharedPreferences,
    application: Application
) : ViewModel() {
    private val keyPrefMinPlayers = application.getString(R.string.KEY_PREF_MIN_PLAYERS)
    private val keyPrefMaxPlayers = application.getString(R.string.KEY_PREF_MAX_PLAYERS)
    private val keyPrefMinPlayTime = application.getString(R.string.KEY_PREF_MIN_PLAYTIME)
    private val keyPrefMaxPlayTime = application.getString(R.string.KEY_PREF_MAX_PLAYTIME)

    private val _randomGame: MutableStateFlow<NetworkResult<GameItems>> = MutableStateFlow(
        NetworkResult.LoadingState
    )
    val randomGame = _randomGame.asStateFlow()


    fun topGames() = topGamesUseCase()

    init {
        randomGames()
    }

    fun randomGames() {
        viewModelScope.launch {
            randomGamesUseCase(
                gt_min_players = pref.getInt(keyPrefMinPlayers, MIN_PLAYERS),
                lt_max_players = pref.getInt(keyPrefMaxPlayers, MAX_PLAYERS),
                gt_min_playtime = pref.getInt(keyPrefMinPlayTime, MIN_PLAYTIME),
                lt_max_playtime = pref.getInt(keyPrefMaxPlayTime, MAX_PLAYTIME)
            ).collect {
                _randomGame.value = it
            }
        }
    }

    fun setDefaultSettings() {
        pref.edit().apply {
            putInt(keyPrefMinPlayers, MIN_PLAYERS)
            putInt(keyPrefMaxPlayers, MAX_PLAYERS)
            putInt(keyPrefMinPlayTime, MIN_PLAYTIME)
            putInt(keyPrefMaxPlayTime, MAX_PLAYTIME)
        }.apply()
    }

}


