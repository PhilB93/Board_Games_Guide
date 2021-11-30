package com.example.boardgamesguide.feature_boardgames.presentation.main


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.domain.prefsstore.PrefsStore
import com.example.boardgamesguide.feature_boardgames.domain.use_case.SearchBoardGamesUseCase
import com.example.boardgamesguide.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class BoardGamesViewModel @Inject constructor(
    private val searchBoardGamesUseCase: SearchBoardGamesUseCase,
    private val prefsStore: PrefsStore
) : ViewModel() {


    val darkThemeEnabled = prefsStore.isNightMode()
    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.toggleNightMode()
        }
    }



    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

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







//
//    private val _searchGames: MutableStateFlow<NetworkResult<List<Game>>> = MutableStateFlow(
//        NetworkResult.EmptyState
//    )
//    val searchGames = _searchGames.asStateFlow()
////    val searchGamesTest = searchBoardGamesUseCase("")
//
//
//    init {
//        getGames("")
//    }
//    private val _state = MutableStateFlow(GameListState())
//    val state =_state.asStateFlow()
//
//    private fun getGames(query:String) {
//        searchBoardGamesUseCase(query).onEach { result ->
//            when (result) {
//                is NetworkResult.DataState -> {
//                    _state.value = GameListState(games = result.data ?: emptyList())
//                }
//                is NetworkResult.ErrorState -> {
//                    _state.value = GameListState(
//                        error = result.message ?: "An unexpected error occured"
//                    )
//                }
//                is NetworkResult.LoadingState -> {
//                    _state.value = GameListState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
//
//        val query = MutableStateFlow("")
//
//
//        val gamesTest = query.flatMapLatest { queryString ->
//            searchBoardGamesUseCase.invoke(queryString)
//        }.flowOn(Dispatchers.IO)
//
//
//        fun searchGamesTest(name: String) {
//            query.value = name
//        }
//
//
//        fun searchGames(name: String) {
//            viewModelScope.launch {
//                searchBoardGamesUseCase.invoke(name).onEach {
//                    _searchGames.value = it
//                }.launchIn(viewModelScope)
//            }
//        }


}




