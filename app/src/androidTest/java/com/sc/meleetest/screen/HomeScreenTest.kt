package com.sc.meleetest.screen

import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLSearchUseCase
import com.sc.meleetest.mock.getTestItem
import com.sc.meleetest.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeScreenTest {

    @Mock
    private lateinit var useCase: MLSearchUseCase

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = HomeViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onQueryNotEmptyClick(): Unit = runBlocking {
        val searchResponse = MLResultStatus.MLSearchResult(listOf(getTestItem()))
        Mockito.`when`(useCase.getSearchResult(Mockito.anyString()))
            .thenReturn(flow {
                //enough time for the test to similate an API response
                delay(500)
                emit(searchResponse)
            })

        composeTestRule.setContent {
            HomeScreen(viewModel,viewModel::searchQuery)
        }
        composeTestRule.onNodeWithText("Buscar").performTextInput("Motorola")
        composeTestRule.onNodeWithContentDescription("Search").performClick()
        assertTrue(viewModel.search.value)
        composeTestRule.onNode(hasStateDescription("Loading")).assertExists()
        delay(500)
        assertFalse(viewModel.search.value)
        composeTestRule.onNode(hasStateDescription("Loading")).assertDoesNotExist()
    }

    @Test
    fun onQueryEmptyClick(): Unit = runBlocking {
        val searchResponse = MLResultStatus.MLSearchResult(listOf(getTestItem()))
        Mockito.`when`(useCase.getSearchResult(Mockito.anyString()))
            .thenReturn(flow {
                emit(searchResponse)
            })

        composeTestRule.setContent {
            HomeScreen(viewModel,viewModel::searchQuery)
        }
        composeTestRule.onNodeWithText("Buscar").performTextInput("")
        composeTestRule.onNodeWithContentDescription("Search").performClick()
        assertFalse(viewModel.search.value)
        composeTestRule.onNode(hasStateDescription("Loading")).assertDoesNotExist()
    }
}