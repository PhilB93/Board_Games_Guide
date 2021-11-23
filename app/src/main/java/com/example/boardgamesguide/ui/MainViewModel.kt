package com.example.boardgamesguide.ui

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.R
import com.example.boardgamesguide.domain.use_case.RandomGamesUseCase
import com.example.boardgamesguide.domain.use_case.TopGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MIN_PLAYERS = 2
private const val MAX_PLAYERS = 10
private const val MAX_PLAYTIME = 120

@HiltViewModel
class MainViewModel @Inject constructor(
    private val topGamesUseCase: TopGamesUseCase,
    private val randomGamesUseCase: RandomGamesUseCase,
    private val pref: SharedPreferences,
    application: Application
) : ViewModel() {
    private val keyPrefMinPlayers = application.getString(R.string.KEY_PREF_MIN_PLAYERS)
    private val keyPrefMaxPlayers = application.getString(R.string.KEY_PREF_MAX_PLAYERS)
    private val keyPrefMaxPlayTime = application.getString(R.string.KEY_PREF_MAX_PLAYTIME)


    fun topGames() = topGamesUseCase()



    fun randomGames() = randomGamesUseCase(
        min_players = pref.getInt(keyPrefMinPlayers, MIN_PLAYERS),
        max_players = pref.getInt(keyPrefMaxPlayers, MAX_PLAYERS),
        lt_max_playtime = pref.getInt(keyPrefMaxPlayTime, MAX_PLAYTIME)
    )

}

