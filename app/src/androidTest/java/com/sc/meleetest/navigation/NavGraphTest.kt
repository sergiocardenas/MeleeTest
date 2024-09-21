package com.sc.meleetest.navigation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation.setViewNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sc.meleetest.fragment.HomeFragment
import com.sc.meleetest.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavGraphTest {

    @Test
    fun fromHomeToSearch() {
        val homeScenario = launchFragmentInContainer<HomeFragment>()

        homeScenario.onFragment { fragment ->
            fragment.activity?.let {activity ->
                val navController = TestNavHostController(activity)
                activity.runOnUiThread { navController.setGraph(R.navigation.nav_graph) }
                setViewNavController(fragment.requireView(), navController)

                navController.navigate(R.id.action_HomeFragment_to_SearchFragment)
                val destination = navController.currentDestination
                assertNotNull(destination)
                assertEquals(destination!!.id, R.id.SearchFragment)
            }
        }
    }

    @Test
    fun fromSearchToHome() {
        val homeScenario = launchFragmentInContainer<HomeFragment>()

        homeScenario.onFragment { fragment ->
            fragment.activity?.let {activity ->
                val navController = TestNavHostController(activity)
                activity.runOnUiThread { navController.setGraph(R.navigation.nav_graph) }
                setViewNavController(fragment.requireView(), navController)

                navController.navigate(R.id.action_HomeFragment_to_SearchFragment)
                assertNotNull(navController.currentDestination)

                navController.popBackStack()
                val destination = navController.currentDestination
                assertNotNull(destination)
                assertEquals(destination!!.id, R.id.HomeFragment)
            }
        }
    }

    @Test
    fun fromSearchToDetail() {
        val homeScenario = launchFragmentInContainer<HomeFragment>()

        homeScenario.onFragment { fragment ->
            fragment.activity?.let {activity ->
                val navController = TestNavHostController(activity)
                activity.runOnUiThread { navController.setGraph(R.navigation.nav_graph) }
                setViewNavController(fragment.requireView(), navController)

                navController.navigate(R.id.action_HomeFragment_to_SearchFragment)
                assertNotNull(navController.currentDestination)

                navController.navigate(R.id.action_SearchFragment_to_DetailFragment)
                val destination = navController.currentDestination
                assertNotNull(destination)
                assertEquals(destination!!.id, R.id.DetailFragment)
            }
        }
    }

    @Test
    fun fromDetailToSearch() {
        val homeScenario = launchFragmentInContainer<HomeFragment>()

        homeScenario.onFragment { fragment ->
            fragment.activity?.let {activity ->
                val navController = TestNavHostController(activity)
                activity.runOnUiThread { navController.setGraph(R.navigation.nav_graph) }
                setViewNavController(fragment.requireView(), navController)

                navController.navigate(R.id.action_HomeFragment_to_SearchFragment)
                assertNotNull(navController.currentDestination)

                navController.navigate(R.id.action_SearchFragment_to_DetailFragment)

                navController.popBackStack()
                val destination = navController.currentDestination
                assertNotNull(destination)
                assertEquals(destination!!.id, R.id.SearchFragment)
            }
        }
    }
}