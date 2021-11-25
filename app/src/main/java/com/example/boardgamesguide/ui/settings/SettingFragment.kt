package com.example.boardgamesguide.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.boardgamesguide.R
import com.example.boardgamesguide.databinding.FragmentMainBinding
import com.example.boardgamesguide.databinding.FragmentSettingBinding
import com.example.boardgamesguide.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {
    private val viewModel by viewModels<MainViewModel>()
    private val binding: FragmentSettingBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUIMode()
     binding.btnLight.setOnClickListener{
         viewModel.toggleNightMode()
     }

    }

    private fun observeUIMode() {
        lifecycleScope.launchWhenCreated {
            viewModel.darkThemeEnabled.collectLatest {
                val defaultMode = if (it) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }

                AppCompatDelegate.setDefaultNightMode(defaultMode)
            }
        }
    }
}