package com.incava.notyourfaultkotlin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.data.ShelterDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShelterViewModel : ViewModel() {

    val isLoadData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>() }
    var totalNum = Int.MAX_VALUE  //데이터의 갯수를 알 수 있는 변수, 초기에는 무조건 받아야하므로 최대 수로 셋팅.
    var shelterList = MutableLiveData<List<Item>>()

    fun loadData() {
        isLoadData.postValue(false)
        val service = RetrofitClient.getInstance().create(APIService::class.java).queryShelter(
            "hSF2++mMiQXwGI5XfZmbSDPqorgTy+jVDuSMNwWmA1gY2h2HASedPbnFIz/eEBlxG8O2nv2vIsz7WSGLeIjWzw==",
            1, 1000, "json"
        )
        service.enqueue(object : Callback<ShelterDTO> {
            override fun onResponse(call: Call<ShelterDTO>, response: Response<ShelterDTO>) {
                var result = response.body()?.response?.header?.resultCode//응답 확인.
                if (result == "0") {
                    shelterList.value = (response.body()?.response?.body?.items?.item ?: return) as List<Item>?//받아온 아이템 배열
                    totalNum = response.body()?.response!!.body?.totalCount?.toInt() ?: -1 // 총 갯수도 파악하기 위해. 변수 세팅.
                    isLoadData.postValue(true)
                }
                Log.i("size", shelterList.value?.size.toString())
            }
            override fun onFailure(call: Call<ShelterDTO>, t: Throwable) {
                Log.i("fail", t.message.toString())
            }
        })
    }

}