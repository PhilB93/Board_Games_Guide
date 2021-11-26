package com.example.boardgamesguide.domain.use_case

import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchBoardGamesUseCase @Inject constructor(
    private val repository: BoardGamesInfoRepository
) {
    operator fun invoke(name: String): Flow<NetworkResult<GameItems>> {
        if (name.isBlank()) {
            return flow { }
        }
        return repository.searchBoardGames(name = name)
    }
}