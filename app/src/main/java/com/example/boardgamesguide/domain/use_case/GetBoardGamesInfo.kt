package com.example.boardgamesguide.domain.use_case


import com.example.boardgamesguide.domain.model.Game
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepositoryImpl
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow


class GetBoardGamesInfo(
    private val boardGamesInfoRepository: BoardGamesInfoRepository
) {

    operator fun invoke(): Flow<NetworkResult<GameItems>> =
        boardGamesInfoRepository.getBoardGamesInfo()

}