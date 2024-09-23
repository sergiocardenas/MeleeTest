package com.sc.meleetest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import com.sc.meleetest.screen.DetailScreen
import com.sc.meleetest.viewmodel.DetailViewModel
import com.sc.meleetest.viewmodel.NavigationViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var sharedViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[NavigationViewModel::class.java]

        detailViewModel.setDetail(sharedViewModel.detailItem.value)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.setContent {
            DetailScreen(detailViewModel) {
                activity?.apply {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        return composeView
    }

}