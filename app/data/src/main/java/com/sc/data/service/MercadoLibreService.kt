package com.sc.data.service

import com.sc.data.RemoteConstants.DETAIL_ENDPOINT
import com.sc.data.RemoteConstants.SEARCH_ENDPOINT
import com.sc.data.response.MLSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreService {

    @GET(SEARCH_ENDPOINT)
    suspend fun getSearch(@Query("q") item: String): Response<MLSearchResponse>

    @GET("$DETAIL_ENDPOINT/{Id}")
    suspend fun getDetail(@Path("Id") id: String): Response<MLSearchResponse>
}