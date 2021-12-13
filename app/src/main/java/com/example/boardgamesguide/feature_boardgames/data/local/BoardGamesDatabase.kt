package com.example.boardgamesguide.feature_boardgames.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.boardgamesguide.feature_boardgames.data.local.entity.BoardGamesEntity

@Database(
    entities = [BoardGamesEntity::class],
    version = 1
)
abstract class BoardGamesDatabase: RoomDatabase() {

    abstract val dao: BoardGamesDao
}