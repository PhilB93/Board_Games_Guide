package com.example.boardgamesguide.feature_boardgames.data.remote.model

import com.example.boardgamesguide.feature_boardgames.data.local.entity.BoardGamesEntity
import com.example.boardgamesguide.feature_boardgames.domain.model.Game

data class GameDto(
    val average_user_rating: Double,
    val id: String,
    val image_url: String,
    val name: String,
    val url: String,
    val year_published: Int
)
{
    fun toBoardGamesEntity(): BoardGamesEntity {
        return BoardGamesEntity(
            average_user_rating =average_user_rating,
            id = id,
            image_url = image_url,
            name = name,
            url = url,
            year_published = year_published
        )
    }
}
fun GameDto.toGame(): Game {
    return Game(
        average_user_rating = average_user_rating,
        id = id,
        image_url = image_url,
        name = name,
        url = url,
        year_published = year_published
    )
}

