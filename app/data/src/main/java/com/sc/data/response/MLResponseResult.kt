package com.sc.data.response

sealed class MLResponseResult {
    data class MLSearchResponseResult(val list: List<MLItemResponse>) : MLResponseResult()
    data class MLDetailResponseResult(val detail: MLItemResponse) : MLResponseResult()
    data class MLResponseError(val message: String) : MLResponseResult()
}
