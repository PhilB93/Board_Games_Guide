package com.example.boardgamesguide.domain.repository



import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface BoardGamesInfoRepository {



    fun searchBoardGames(
        name:String
    ): Flow<NetworkResult<GameItems>>

}