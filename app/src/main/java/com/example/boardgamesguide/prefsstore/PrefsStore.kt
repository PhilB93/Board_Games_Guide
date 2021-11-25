package com.example.boardgamesguide.prefsstore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
  fun isNightMode(): Flow<Boolean>

  suspend fun toggleNightMode()
}