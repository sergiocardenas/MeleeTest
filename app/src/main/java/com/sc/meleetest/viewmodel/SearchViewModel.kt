package com.sc.meleetest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLDetailUseCase
import com.sc.meleetest.mapper.toDetailState
import com.sc.meleetest.state.MLDetailItemState
import com.sc.meleetest.state.MLSearchItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: MLDetailUseCase
): ViewModel() {

    private val _searchList = MutableStateFlow<List<MLSearchItemState>>(mutableListOf())
    val searchList: StateFlow<List<MLSearchItemState>> = _searchList

    private val _detailItem = MutableStateFlow<MLDetailItemState?>(null)
    val detailItem: StateFlow<MLDetailItemState?> = _detailItem

    fun setSearchList(list: List<MLSearchItemState>){
        _searchList.value = list
    }

    fun fetchDetail(id: String){
        if(id.isNotEmpty()){
            viewModelScope.launch {
                useCase.getDetailResult(id).collect { resultStatus ->
                    when(resultStatus){
                        is MLResultStatus.MLDetailResult ->{
                            resultStatus.detail.let { searchResult ->
                                _detailItem.value = searchResult.toDetailState()
                            }
                        }
                        is MLResultStatus.MLError ->{
                            _detailItem.value = null
                        }
                        else -> {
                            _detailItem.value = null
                        }
                    }
                }
            }
        }else{

        }
    }
}