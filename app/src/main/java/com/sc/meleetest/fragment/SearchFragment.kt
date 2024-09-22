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
import com.sc.meleetest.screen.SearchScreen
import com.sc.meleetest.viewmodel.NavigationViewModel
import com.sc.meleetest.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var sharedViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[NavigationViewModel::class.java]

        searchViewModel.setSearchList(sharedViewModel.searchList.value)
        sharedViewModel.clearSearch()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.setContent {
            SearchScreen () {
                searchViewModel.fetchDetail("01")
                goToDetail()
            }
        }
        return composeView
    }


    fun goToDetail(){
        sharedViewModel.passDetail(searchViewModel.detailItem.value!!)
        findNavController().navigate(
            resId = R.id.action_SearchFragment_to_DetailFragment
        )
    }

}