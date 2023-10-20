package com.incava.notyourfaultkotlin

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * 레트로핏 헬퍼를 써놓은 오브젝트 싱글턴. 인스턴스를 편하게 쓰기위해 사용.
 */

object RetrofitClient {
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/1383000/gmis/teenRAreaServiceV2/") //기본 baseUrl
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return instance!!
    }

}