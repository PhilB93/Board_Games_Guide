package com.example.boardgamesguide.feature_boardgames.domain.use_case

import com.example.boardgamesguide.feature_boardgames.domain.repository.BoardGamesInfoRepository

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class SearchBoardGamesUseCase @Inject constructor(
    private val repository: BoardGamesInfoRepository
) {
    operator fun invoke(name: String) = repository.searchBoardGames(name).flowOn(Dispatchers.IO)

}