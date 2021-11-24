package com.example.boardgamesguide.ui.details

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.R
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.use_case.GetBoardGameByIdUseCase
import com.example.boardgamesguide.domain.use_case.RandomGamesUseCase
import com.example.boardgamesguide.domain.use_case.TopGamesUseCase
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class DetailsViewModel @Inject constructor(
  private val getBoardGameByIdUseCase: GetBoardGameByIdUseCase
) : ViewModel() {

//    private val _randomGame: MutableStateFlow<NetworkResult<GameItems>> = MutableStateFlow(
//        NetworkResult.LoadingState
//    )
//
//    val randomGame = _randomGame.asStateFlow()
//
//    fun topGames() = topGamesUseCase()
//
//    init {
//        randomGames()
//    }
//
//    fun randomGames() {
//        viewModelScope.launch {
//            randomGamesUseCase(
//                min_players = pref.getInt(keyPrefMinPlayers, MIN_PLAYERS),
//                max_players = pref.getInt(keyPrefMaxPlayers, MAX_PLAYERS),
//                lt_max_playtime = pref.getInt(keyPrefMaxPlayTime, MAX_PLAYTIME)
//            ).collect {
//                _randomGame.value = it
//            }
//        }
//
//    }
}

