package com.sc.meleetest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.sc.meleetest.R
import com.sc.meleetest.screen.SearchScreen

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.setContent {
            SearchScreen () {
                goToDetail()
            }
        }
        return composeView
    }


    fun goToDetail(){
        findNavController().navigate(
            resId = R.id.action_SearchFragment_to_DetailFragment
        )
    }

}