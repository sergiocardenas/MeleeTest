package com.sc.meleetest.viewmodel

import androidx.lifecycle.ViewModel
import com.sc.meleetest.state.MLDetailItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel (): ViewModel() {

    private val _detailItem = MutableStateFlow<MLDetailItemState?>(null)
    val detailItem: StateFlow<MLDetailItemState?> = _detailItem

    fun setDetail(item: MLDetailItemState?){
        _detailItem.value = item
    }
}