package com.example.boardgamesguide.network



import com.example.boardgamesguide.domain.model.GameItems
import retrofit2.http.GET


interface ApiService {
    @GET("api/search?client_id=JLBr5npPhV")
    suspend fun getCatItems(
    ): GameItems

}
