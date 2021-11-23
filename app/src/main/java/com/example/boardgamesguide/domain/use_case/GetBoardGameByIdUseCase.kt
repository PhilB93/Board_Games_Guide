package com.example.boardgamesguide.domain.use_case

import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoardGameByIdUseCase @Inject constructor(
    private val repository: BoardGamesInfoRepository
)
{
    operator fun invoke(id:String): Flow<NetworkResult<GameItems>> =
        repository.getBoardGameById(id = id)
}