package com.example.boardgamesguide.feature_boardgames.domain.prefsstore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
  fun isNightMode(): Flow<Boolean>
  suspend fun toggleNightMode()
}