package com.example.boardgamesguide.feature_boardgames.data.remote

import com.example.boardgamesguide.feature_boardgames.data.remote.model.GameItemsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/search")
    suspend fun searchBoardGames(
        @Query("name") name: String,
        @Query("exact") exact: Boolean = true,
        @Query("client_id") client_id: String = CLIENT_ID
    ): GameItemsDto

    @GET("api/search")
    suspend fun getBoardGames(
        @Query("order_by") order_by: String = "rank",
        @Query("ascending") ascending: Boolean = false,
        @Query("limit") limit: Int = LIMIT,
        @Query("client_id") client_id: String = CLIENT_ID
    ): GameItemsDto

    companion object {
        const val CLIENT_ID = "JLBr5npPhV"
        const val LIMIT = 10
    }
}
