package com.example.boardgamesguide.network


import com.example.boardgamesguide.domain.model.GameItems
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("api/search")
    suspend fun topBoardGames(
        @Query("limit") limit: Int,
        @Query("order_by") order_by: String,
        @Query("client_id") client_id: String,
    ): GameItems

    @GET("api/search?")
    suspend fun randomBoardGames(
        @Query("limit") limit: Int,
        @Query("min_players") min_players: Int,
        @Query("max_players") max_players: Int,
        @Query("lt_max_playtime") lt_max_playtime: Int,
        @Query("client_id") client_id: String,
    ): GameItems

}
