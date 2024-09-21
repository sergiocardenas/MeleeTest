package com.sc.domain.usecase

import com.sc.domain.model.MLResultStatus
import com.sc.domain.repository.MLRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MLDetailUseCaseImp @Inject constructor(
    private val repository: MLRepository
) : MLDetailUseCase{
    override suspend fun getDetailResult(id: String): Flow<MLResultStatus> {
        return repository.getDetailResult(id).map { it ->
            if(it != null){
                MLResultStatus.MLDetailResult(it)
            }else{
                MLResultStatus.MLError("No item found")
            }
        }
    }
}