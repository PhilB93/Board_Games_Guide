package com.example.boardgamesguide.domain.use_case

import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow

class RandomGamesUseCase(
    private val boardGamesInfoRepository: BoardGamesInfoRepository,
) {

    operator fun invoke(
        min_players: Int,
        max_players: Int,
        lt_max_playtime: Int
    ): Flow<NetworkResult<GameItems>> {

        return boardGamesInfoRepository.randomBoardGameInfo(
            min_players,
            max_players,
            lt_max_playtime
        )
    }
}