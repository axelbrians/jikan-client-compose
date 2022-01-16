package com.machina.jikan_client_compose.domain.use_case.get_top_anime

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImpl
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeTopState
import io.mockk.coEvery
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


@RunWith(JUnit4::class)
class GetTopAnimeUseCaseTest {

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private val animeRepository = mockk<AnimeRepositoryImpl>()

  @ExperimentalCoroutinesApi
  private val testDispatchers = DefaultDispatchers(
    TestCoroutineDispatcher(),
    TestCoroutineDispatcher(),
    TestCoroutineDispatcher(),
    TestCoroutineDispatcher(),
    TestCoroutineDispatcher()
  )

  @ExperimentalCoroutinesApi
  private val topAnimeUseCaseKtor by lazy { GetTopAnimeUseCase(animeRepository, testDispatchers) }


  @Test
  @ExperimentalCoroutinesApi
  fun `get top anime use case returns success`() = runBlockingTest {
    // Prepare
    val expectedResult = AnimeTopState(emptyList(), false, Event(null))

    coEvery { animeRepository.getTopAnimeList() } returns Resource.Success(emptyList())

    // Execute
    val result = topAnimeUseCaseKtor().drop(1).first()

    // Assert
    assertThat(result.toString()).isEqualTo(expectedResult.toString())
  }

  @Test
  @ExperimentalCoroutinesApi
  fun `get top anime use case returns error`() = runBlockingTest {
    // Prepare
    val errorMessage = "Something went wrong"
    val expectedResult = AnimeTopState(emptyList(), false, Event(errorMessage))

    coEvery { animeRepository.getTopAnimeList() } returns Resource.Error(errorMessage)

    // Execute
    val result = topAnimeUseCaseKtor().drop(1).first()

    // Assert
    assertThat(result.toString()).isEqualTo(expectedResult.toString())
  }



}