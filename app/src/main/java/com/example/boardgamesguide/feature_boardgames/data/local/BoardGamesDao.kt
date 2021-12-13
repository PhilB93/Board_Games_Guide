package com.example.boardgamesguide.feature_boardgames.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boardgamesguide.feature_boardgames.data.local.entity.BoardGamesEntity

@Dao
interface BoardGamesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoardGames(games: List<BoardGamesEntity>)

    @Query("DELETE FROM boardgamesentity  WHERE name IN(:names)")
    suspend fun deleteBoardGames(names: List<String>)

    @Query("SELECT * FROM boardgamesentity WHERE name LIKE '%' || :name || '%'")
    suspend fun searchBoardGames(name: String): List<BoardGamesEntity>

    @Query("SELECT * FROM boardgamesentity")
    suspend fun getBoardGames(): List<BoardGamesEntity>
}