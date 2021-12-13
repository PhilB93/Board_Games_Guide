package com.example.boardgamesguide.feature_boardgames.presentation.sign

import com.example.boardgamesguide.feature_boardgames.domain.login.User

data class LoginState(
    val isLoading: Boolean = false,
    val data: User? = null,
    val error: String = ""
)