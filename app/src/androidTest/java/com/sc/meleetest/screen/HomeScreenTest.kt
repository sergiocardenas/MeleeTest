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
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onSearchClick() {
        var doSearch = false
        val click : () -> Unit = { doSearch = true}
        composeTestRule.setContent {
            HomeScreen(click)
        }
        composeTestRule.onNodeWithText("Go to SearchFragment").performClick()
        Assert.assertTrue(doSearch)
    }
}