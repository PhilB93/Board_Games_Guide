package com.example.boardgamesguide.feature_boardgames.domain.use_case

import com.example.boardgamesguide.feature_boardgames.domain.repository.BoardGamesInfoRepository

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class TopBoardGamesUseCase @Inject constructor(
    private val repository: BoardGamesInfoRepository
) {
    operator fun invoke() = repository.getBoardGames().flowOn(Dispatchers.IO)

}