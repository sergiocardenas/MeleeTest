package com.sc.meleetest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLSearchUseCase
import com.sc.meleetest.mapper.toListState
import com.sc.meleetest.state.MLSearchItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ERROR_MESSAGE_TIME = 2000L

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

    fun searchQuery(query: String){
        if(query.isNotEmpty()){
            _search.value = true
            viewModelScope.launch {
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
                            }
                        }
                        is MLResultStatus.MLError ->{
                            _list.value = mutableListOf()
                        }
                        else -> {
                            _list.value = mutableListOf()
                        }
                    }
                }
            }
        }else{
        }
    }

    private suspend fun updateErrorMessage(message: String){
        _errorMessage.value = message
        delay(ERROR_MESSAGE_TIME)
        _errorMessage.value = ""
    }
}