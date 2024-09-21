package com.sc.meleetest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.sc.meleetest.R
import com.sc.meleetest.screen.HomeScreen

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.setContent {
            HomeScreen() {
                goToSearch()
            }
        }
        return composeView
    }

    fun goToSearch(){
        findNavController().navigate(
            resId = R.id.action_HomeFragment_to_SearchFragment
        )
    }
}