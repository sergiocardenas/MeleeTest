package com.sc.domain.usecase

import com.sc.domain.model.MLResultStatus
import com.sc.domain.repository.MLRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MLSearchUseCaseImp @Inject constructor(
    private val repository: MLRepository
) : MLSearchUseCase {
    override suspend fun getSearchResult(query: String): Flow<MLResultStatus> {
        return repository.getSearchResult(query).map { it ->
            if(it != null){
                MLResultStatus.MLSearchResult(it)
            }else{
                MLResultStatus.MLError("Search Error")
            }
        }
    }
}