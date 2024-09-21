package com.sc.meleetest.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
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
        composeTestRule.onNodeWithText("Go to DetailFragment").performClick()
        Assert.assertTrue(goDetail)
    }
}