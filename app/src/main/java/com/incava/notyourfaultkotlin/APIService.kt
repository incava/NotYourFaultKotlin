package com.incava.notyourfaultkotlin

import com.incava.notyourfaultkotlin.data.ShelterDTO
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService {
    @GET
    suspend fun queryShelter(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("type") type: String
    ): Response<ShelterDTO>


}