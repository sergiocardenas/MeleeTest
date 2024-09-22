package com.sc.meleetest.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.sc.domain.usecase.MLDetailUseCase
import com.sc.meleetest.mock.getTestSearchItem
import com.sc.meleetest.utils.EMPTY_SEARCH_MESSAGE
import com.sc.meleetest.utils.IMAGE_LOGO_DESCRIPTION
import com.sc.meleetest.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SearchScreenTest {

    @Mock
    private lateinit var useCase: MLDetailUseCase

    private lateinit var viewModel: SearchViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        Dispatchers.resetMain()
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = SearchViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onStartFilledList() {
        val item = getTestSearchItem()
        viewModel.setSearchList(listOf(getTestSearchItem()))

        composeTestRule.setContent {
            SearchScreen(viewModel){}
        }

        composeTestRule.onNodeWithText(item.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(item.currency_id +" "+item.price.toString()).assertIsDisplayed()
    }

    @Test
    fun onStartEmptyList() {
        viewModel.setSearchList(listOf())

        composeTestRule.setContent {
            SearchScreen(viewModel){}
        }

        composeTestRule.onNodeWithText(EMPTY_SEARCH_MESSAGE).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(IMAGE_LOGO_DESCRIPTION).assertIsDisplayed()
    }
}