package com.sc.domain.model

sealed class MLResultStatus {
    data class MLSearchResult(val list: List<MLItemModel>) : MLResultStatus()
    data class MLDetailResult(val detail: MLItemModel) : MLResultStatus()
    data class MLError(val message: String) : MLResultStatus()
}
