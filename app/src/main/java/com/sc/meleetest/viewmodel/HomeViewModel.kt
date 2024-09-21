package com.sc.meleetest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc.domain.model.MLResultStatus
import com.sc.domain.usecase.MLSearchUseCase
import com.sc.meleetest.mapper.toListState
import com.sc.meleetest.state.MLSearchItemState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun searchQuery(query: String){
        if(query.isNotEmpty()){
            viewModelScope.launch {
                useCase.getSearchResult(query).collect{ resultStatus ->
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
}