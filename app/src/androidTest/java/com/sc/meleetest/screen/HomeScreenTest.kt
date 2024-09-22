package com.sc.meleetest.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLSearchUseCase
import com.sc.meleetest.mock.getTestItem
import com.sc.meleetest.utils.EMPTY_QUERY_MESSAGE
import com.sc.meleetest.utils.SEARCH_BUTTON_DESCRIPTION
import com.sc.meleetest.utils.SEARCH_LOADIND_DESCRIPTION
import com.sc.meleetest.utils.SEARCH_TEXT_FIELD_HINT
import com.sc.meleetest.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
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
    fun onQueryEmptyClick(): Unit = runBlocking {
        val searchResponse = MLResultStatus.MLSearchResult(listOf(getTestItem()))
        Mockito.`when`(useCase.getSearchResult(Mockito.anyString()))
            .thenReturn(flow {
                emit(searchResponse)
            })

        composeTestRule.setContent {
            HomeScreen(viewModel)
        }
        composeTestRule.onNodeWithText(SEARCH_TEXT_FIELD_HINT).performTextInput("")
        composeTestRule.onNodeWithContentDescription(SEARCH_BUTTON_DESCRIPTION).performClick()
        assertFalse(viewModel.search.value)
        composeTestRule.onNode(hasStateDescription(SEARCH_LOADIND_DESCRIPTION)).assertDoesNotExist()
        assertEquals(viewModel.errorMessage.value, EMPTY_QUERY_MESSAGE)
        composeTestRule.onNode(hasText(EMPTY_QUERY_MESSAGE), useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun onQuerySuccess(): Unit = runBlocking {
        val searchResponse = MLResultStatus.MLSearchResult(listOf(getTestItem()))
        Mockito.`when`(useCase.getSearchResult(Mockito.anyString()))
            .thenReturn(flow {
                //enough time for the test to similate an API response
                delay(1000)
                emit(searchResponse)
            })

        composeTestRule.setContent {
            HomeScreen(viewModel)
        }
        composeTestRule.onNodeWithText(SEARCH_TEXT_FIELD_HINT).performTextInput("Motorola")
        composeTestRule.onNodeWithContentDescription(SEARCH_BUTTON_DESCRIPTION).performClick()
        assertTrue(viewModel.search.value)
        composeTestRule.onNode(hasStateDescription(SEARCH_LOADIND_DESCRIPTION)).assertExists()
        delay(1000)
        assertFalse(viewModel.search.value)
        composeTestRule.onNode(hasStateDescription(SEARCH_LOADIND_DESCRIPTION)).assertDoesNotExist()
    }

    @Test
    fun onQueryError(): Unit = runBlocking {
        val errorMsg = "Error de test"
        val searchResponse = MLResultStatus.MLError(errorMsg)
        Mockito.`when`(useCase.getSearchResult(Mockito.anyString()))
            .thenReturn(flow {
                //enough time for the test to similate an API response
                delay(1000)
                emit(searchResponse)
            })

        composeTestRule.setContent {
            HomeScreen(viewModel)
        }
        composeTestRule.onNodeWithText(SEARCH_TEXT_FIELD_HINT).performTextInput("Motorola")
        composeTestRule.onNodeWithContentDescription(SEARCH_BUTTON_DESCRIPTION).performClick()
        assertTrue(viewModel.search.value)
        composeTestRule.onNode(hasStateDescription(SEARCH_LOADIND_DESCRIPTION)).assertExists()
        delay(1000)
        assertFalse(viewModel.search.value)
        composeTestRule.onNode(hasStateDescription(SEARCH_LOADIND_DESCRIPTION)).assertDoesNotExist()
        assertEquals(viewModel.errorMessage.value, errorMsg)
        composeTestRule.onNode(hasText(errorMsg), useUnmergedTree = true).assertIsDisplayed()
    }
}