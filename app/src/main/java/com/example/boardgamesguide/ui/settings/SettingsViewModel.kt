package com.example.boardgamesguide.ui.settings


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
private  val settingsManager: SettingsManager
    ) :
    ViewModel() {

    val preferencesFlow = settingsManager.uiModeFlow

    fun onHideCompletedClick(uiMode: UIMode) = viewModelScope.launch {
        settingsManager.setUIMode(uiMode)
    }



}