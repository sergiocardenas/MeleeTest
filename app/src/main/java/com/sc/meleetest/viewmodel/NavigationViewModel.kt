package com.sc.meleetest.viewmodel

import androidx.lifecycle.ViewModel
import com.sc.meleetest.state.MLDetailItemState
import com.sc.meleetest.state.MLSearchItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavigationViewModel(): ViewModel() {

    private val _searchList = MutableStateFlow<List<MLSearchItemState>>(mutableListOf())
    val searchList: StateFlow<List<MLSearchItemState>> = _searchList

    private val _detailItem = MutableStateFlow<MLDetailItemState?>(null)
    val detailItem: StateFlow<MLDetailItemState?> = _detailItem

    fun passSearch(list: List<MLSearchItemState>){
        _searchList.value = list
    }

    fun passDetail(item: MLDetailItemState){
        _detailItem.value = item
    }
}