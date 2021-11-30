package com.example.boardgamesguide.feature_boardgames.data.remote


import com.example.boardgamesguide.feature_boardgames.data.model.GameItemsDto
import com.example.boardgamesguide.feature_boardgames.domain.model.GameItems
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("api/search")
    suspend fun searchBoardGames(
        @Query("name") name: String,
        @Query("exact") exact: Boolean = true,
        @Query("client_id") client_id: String = CLIENT_ID
    ): GameItemsDto
    companion object {
        const val CLIENT_ID = "JLBr5npPhV"
    }
}
