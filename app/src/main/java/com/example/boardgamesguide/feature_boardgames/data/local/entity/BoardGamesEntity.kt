package com.example.boardgamesguide.feature_boardgames.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.boardgamesguide.feature_boardgames.domain.model.Game

@Entity
data class BoardGamesEntity(
    val average_user_rating: Double,
    val image_url: String,
    val name: String,
    val url: String,
    val year_published: Int,
    @PrimaryKey val id: String
) {
    fun toGame(): Game {
        return Game(
            id =id,
            average_user_rating = average_user_rating,
            image_url = image_url,
            name = name,
            url = url,
            year_published = year_published
        )
    }
}