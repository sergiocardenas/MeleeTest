package com.sc.meleetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLDetailUseCase
import com.sc.meleetest.mapper.toDetailState
import com.sc.meleetest.state.MLDetailItemState
import com.sc.meleetest.state.MLSearchItemState
import com.sc.meleetest.utils.EMPTY_DETAIL_DETAIL_MESSAGE
import com.sc.meleetest.utils.ERROR_MESSAGE_TIME
import com.sc.meleetest.utils.UNKNOWN_ERROR_SEARCH_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val _search = MutableStateFlow<Boolean>(false)
    val search: StateFlow<Boolean> = _search

    private val _detailItem = MutableStateFlow<MLDetailItemState?>(null)
    val detailItem: StateFlow<MLDetailItemState?> = _detailItem

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _fetchSuccess = MutableLiveData<Boolean>(false)
    val fetchSuccess: LiveData<Boolean> = _fetchSuccess

    fun setSearchList(list: List<MLSearchItemState>){
        _searchList.value = list
    }

    fun fetchDetail(id: String){
        viewModelScope.launch {
            if(id.isNotEmpty()){
                _search.value = true
                useCase.getDetailResult(id).collect { resultStatus ->
                    _search.value = false
                    when(resultStatus){
                        is MLResultStatus.MLDetailResult ->{
                            resultStatus.detail.let { searchResult ->
                                _detailItem.value = searchResult.toDetailState()
                            }
                            goToDetailPage()
                        }
                        is MLResultStatus.MLError ->{
                            _detailItem.value = null
                            updateErrorMessage(resultStatus.message)
                        }
                        else -> {
                            _detailItem.value = null
                            updateErrorMessage(UNKNOWN_ERROR_SEARCH_MESSAGE)
                        }
                    }
                }
            }else{
                updateErrorMessage(EMPTY_DETAIL_DETAIL_MESSAGE)
            }
        }
    }

    private suspend fun updateErrorMessage(message: String){
        _errorMessage.value = message
        delay(ERROR_MESSAGE_TIME)
        _errorMessage.value = ""
    }

    private suspend fun goToDetailPage(){
        _fetchSuccess.postValue(true)
    }

    fun resetSearch(){
        _fetchSuccess.value = false
        _detailItem.value = null
    }
}