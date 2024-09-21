package com.sc.meleetest.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onDetailClick() {
        var goDetail = false
        val click : () -> Unit = { goDetail = true}
        composeTestRule.setContent {
            HomeScreen(click)
        }
        composeTestRule.onNodeWithText("Go to SearchFragment").performClick()
        Assert.assertTrue(goDetail)
    }
}