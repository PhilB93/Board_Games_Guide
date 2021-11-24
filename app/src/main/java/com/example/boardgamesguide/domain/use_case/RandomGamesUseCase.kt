package com.example.boardgamesguide.domain.use_case

import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow

class RandomGamesUseCase(
    private val boardGamesInfoRepository: BoardGamesInfoRepository,
) {

    operator fun invoke(
        gt_min_players: Int,
        lt_max_players: Int,
        gt_min_playtime:Int,
        lt_max_playtime:Int
    ): Flow<NetworkResult<GameItems>> = boardGamesInfoRepository.randomBoardGameInfo(
        gt_min_players,
        lt_max_players,
        gt_min_playtime,
        lt_max_playtime
    )
}
