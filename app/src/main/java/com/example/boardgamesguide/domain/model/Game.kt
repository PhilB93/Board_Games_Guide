package com.example.boardgamesguide.domain.model

data class Game(
    val average_learning_complexity: Double,
    val average_strategy_complexity: Double,
    val average_user_rating: Double,
    val categories: List<Category>,
    val description: String,
    val description_preview: String,
    val id: String,
    val image_url: String,
    val max_players: Int,
    val max_playtime: Int,
    val min_age: Int,
    val min_players: Int,
    val min_playtime: Int,
    val name: String,
    val official_url: String,
    val price_text: String,
    val rules_url: String,
    val tags: List<String>,
    val url: String,
)