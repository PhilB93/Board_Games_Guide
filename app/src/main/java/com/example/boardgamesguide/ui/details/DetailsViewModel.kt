package com.example.boardgamesguide.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.use_case.GetBoardGameByIdUseCase
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

  private val _chosenGameById: MutableStateFlow<NetworkResult<GameItems>> = MutableStateFlow(
    NetworkResult.LoadingState
  )

  val chosenGameById = _chosenGameById.asStateFlow()


  private fun getGameById(id:String) {
    viewModelScope.launch {
    getBoardGameByIdUseCase(id = id).collect {
      _chosenGameById.value =it
    }
    }
  }
}

