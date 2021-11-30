package com.example.boardgamesguide.feature_boardgames.data.model

import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.domain.model.GameItems


data class GameItemsDto(
    val games: List<GameDto>
)


fun GameItemsDto.toGameItems(): GameItems
= GameItems(games.map { it.toGame()})





//fun GameItemsDto.toGameItems(): GameItems {
//    return GameItems(
//        games = games.map{
//            Game
//            (it.average_user_rating,
//            it.id,
//            it.image_url,
//            it.name,
//            it.url,
//            it.year_published)
//        }
//    )
//}