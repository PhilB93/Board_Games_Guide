package com.example.boardgamesguide.feature_boardgames.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.boardgamesguide.feature_boardgames.data.prefstore.PrefsStoreImpl
import com.example.boardgamesguide.feature_boardgames.data.repository.BoardGamesInfoRepositoryImpl
import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.domain.use_case.SearchBoardGamesUseCase
import com.example.boardgamesguide.util.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class BoardGamesViewModelTest
{
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: BoardGamesViewModel

    @MockK
    private lateinit var repository: BoardGamesInfoRepositoryImpl

    @MockK
    private lateinit var searchBoardGamesUseCase: SearchBoardGamesUseCase

    @MockK
    private lateinit var prefsStoreImpl: PrefsStoreImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = BoardGamesViewModel(searchBoardGamesUseCase ,prefsStoreImpl)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when search is called, flow value is set`() = runBlockingTest {


          }



    }



