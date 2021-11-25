package com.example.boardgamesguide.network


import com.example.boardgamesguide.domain.model.GameItems
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("api/search")
    suspend fun topBoardGames(
        @Query("limit") limit: Int,
        @Query("order_by") order_by: String,
        @Query("client_id") client_id: String = CLIENT_ID
    ): GameItems

    @GET("api/search")
    suspend fun randomBoardGames(
        @Query("random") random: Boolean = true,
        @Query("client_id") client_id: String = CLIENT_ID
    ): GameItems

    @GET("api/search")
    suspend fun getGameById(
        @Query("id") id: String,
        @Query("client_id") client_id: String = CLIENT_ID
    ): GameItems

    companion object {
        const val CLIENT_ID = "JLBr5npPhV"
    }
}
