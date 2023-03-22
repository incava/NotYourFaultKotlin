package com.incava.notyourfaultkotlin
import com.incava.notyourfaultkotlin.data.ShelterDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 레트로핏으로 GET을 통해 조회하는 인터페이스
 */
interface APIService {
    @GET("getTeenRAreaList")
     fun queryShelter(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("type") type: String
    ): Call<ShelterDTO>


}