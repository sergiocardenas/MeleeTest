package com.sc.meleetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLSearchUseCase
import com.sc.meleetest.mapper.toListState
import com.sc.meleetest.state.MLSearchItemState
import com.sc.meleetest.utils.EMPTY_QUERY_MESSAGE
import com.sc.meleetest.utils.ERROR_MESSAGE_TIME
import com.sc.meleetest.utils.UNKNOWN_ERROR_SEARCH_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: MLSearchUseCase,
) : ViewModel() {

    private val _list = MutableStateFlow<List<MLSearchItemState>>(mutableListOf())
    val list: StateFlow<List<MLSearchItemState>> = _list

    private val _search = MutableStateFlow<Boolean>(false)
    val search: StateFlow<Boolean> = _search

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _searchSuccess = MutableLiveData<Boolean>(false)
    val searchSuccess: LiveData<Boolean> = _searchSuccess

    fun searchQuery(query: String){
        viewModelScope.launch {
            if(query.isNotEmpty()){
                _search.value = true
                useCase.getSearchResult(query).collect{ resultStatus ->
                    _search.value = false
                    when(resultStatus){
                        is MLResultStatus.MLSearchResult ->{
                            resultStatus.list.let { searchResult ->
                                if(searchResult.isNotEmpty()){
                                    _list.value = searchResult.map { it.toListState() }
                                }else{
                                    _list.value = mutableListOf()
                                }
                                goToSearchPage()
                            }
                        }
                        is MLResultStatus.MLError ->{
                            _list.value = mutableListOf()
                            updateErrorMessage(resultStatus.message)
                        }
                        else -> {
                            _list.value = mutableListOf()
                            updateErrorMessage(UNKNOWN_ERROR_SEARCH_MESSAGE)
                        }
                    }
                }
            }else{
                updateErrorMessage(EMPTY_QUERY_MESSAGE)
            }
        }
    }

    private suspend fun updateErrorMessage(message: String){
        _errorMessage.value = message
        delay(ERROR_MESSAGE_TIME)
        _errorMessage.value = ""
    }

    private suspend fun goToSearchPage(){
        _searchSuccess.postValue(true)
    }

    fun resetSearch(){
        _searchSuccess.value = false
        _list.value = listOf()
    }
}