package com.example.boardgamesguide.ui

import androidx.lifecycle.ViewModel
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepositoryImpl
import com.example.boardgamesguide.domain.use_case.GetBoardGamesInfo
import com.example.boardgamesguide.util.NetworkResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBoardGamesInfo: GetBoardGamesInfo
): ViewModel() {


    fun getSampleResponse() = getBoardGamesInfo()
}