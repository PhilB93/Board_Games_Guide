package com.example.boardgamesguide.feature_boardgames.domain.repository



import com.example.boardgamesguide.feature_boardgames.domain.model.GameItems
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface BoardGamesInfoRepository {

    fun searchBoardGames(
        name:String
    ): Flow<NetworkResult<GameItems>>

}