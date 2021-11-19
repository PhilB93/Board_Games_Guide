package com.example.boardgamesguide

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.boardgamesguide.databinding.ActivityMainBinding
import com.example.boardgamesguide.ui.MainViewModel
import com.example.boardgamesguide.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    val mainViewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchData() {
        binding.apply {
            lifecycleScope.launchWhenCreated {
                delay(500)
                mainViewModel.getSampleResponse()
                    .collect {
                        when (it) {
                            is NetworkResult.DataState -> {
                                pbDog.isVisible =false
                                tvText.text = "success ${it.data.games}}"
                            }
                            is NetworkResult.ErrorState ->
                            {
                                pbDog.isVisible =false
                                tvText.text =  "error ${it.exception}"
                            }
                            is NetworkResult.LoadingState -> pbDog.isVisible = true
                        }

                    }
            }
        }
    }


}

