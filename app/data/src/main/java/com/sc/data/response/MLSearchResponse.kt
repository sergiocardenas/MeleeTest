package com.sc.data.response

data class MLSearchResponse(
    val site_id: String,
    val country_default_time_zone: String,
    val query: String,
    val results: List<MLItemResponse> = listOf(),
)
