package com.example.boardgamesguide.ui.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.R
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.use_case.RandomGamesUseCase
import com.example.boardgamesguide.domain.use_case.SearchBoardGamesUseCase
import com.example.boardgamesguide.domain.use_case.TopGamesUseCase
import com.example.boardgamesguide.prefsstore.PrefsStore
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.internal.Contexts.getApplication
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
    private val searchBoardGamesUseCase: SearchBoardGamesUseCase,
    private val prefsStore: PrefsStore
) : ViewModel() {


    private val _randomGame: MutableStateFlow<NetworkResult<GameItems>> = MutableStateFlow(
        NetworkResult.LoadingState
    )
    val randomGame = _randomGame.asStateFlow()

    val darkThemeEnabled = prefsStore.isNightMode()

    private val _searchGames: MutableStateFlow<NetworkResult<GameItems>> = MutableStateFlow(
        NetworkResult.EmptyState
    )
    val searchGames = _searchGames.asStateFlow()


    fun topGames() = topGamesUseCase()

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


