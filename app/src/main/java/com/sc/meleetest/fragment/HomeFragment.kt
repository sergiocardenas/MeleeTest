package com.sc.meleetest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sc.meleetest.R
import com.sc.meleetest.screen.HomeScreen
import com.sc.meleetest.viewmodel.HomeViewModel
import com.sc.meleetest.viewmodel.NavigationViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[NavigationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.setContent {
            HomeScreen(
                homeViewModel,
                homeViewModel::searchQuery
            )
        }
        return composeView
    }

    fun goToSearch(){
        sharedViewModel.passSearch(homeViewModel.list.value)
        findNavController().navigate(
            resId = R.id.action_HomeFragment_to_SearchFragment
        )
    }
}