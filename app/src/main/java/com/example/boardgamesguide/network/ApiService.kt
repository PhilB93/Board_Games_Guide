package com.example.boardgamesguide.network


import com.example.boardgamesguide.domain.model.GameItems
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {
    @GET("api/search")
    suspend fun topBoardGames(
        @Query("limit") limit: Int,
        @Query("order_by") order_by: String,
        @Query("client_id") client_id: String = CLIENT_ID
    ): GameItems

    @GET("api/search?random=true&limit=1")
    suspend fun randomBoardGames(
        //  @Query("limit") limit: Int,
        //  @Query("min_players") min_players: Int,
        //  @Query("max_players") max_players: Int,
        //    @Query("lt_max_playtime") lt_max_playtime: Int,
        //    @Query("client_id") client_id: String,
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
