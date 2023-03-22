package com.incava.notyourfaultkotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.data.ShelterDTO
import com.incava.notyourfaultkotlin.database.ShelterDAO
import com.incava.notyourfaultkotlin.database.ShelterDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Room을 관리하기 위한 ViewModel. application의 권한을 받기 위해 AndroidViewModel을 사용.
 */
class ShelterViewModel(application: Application) : AndroidViewModel(application) {
    private val shelterDao = ShelterDatabase.getInstance(application)!!.shelterDao()
    var totalNum = Int.MAX_VALUE // 데이터의 갯수를 알 수 있는 변수, 초기에는 무조건 받아야하므로 최대 수로 셋팅.
    val shelterList : MutableLiveData<List<Item>> by lazy{
        MutableLiveData<List<Item>>()
    }
    val shelterFilterList : MutableLiveData<List<Item>> by lazy{
        MutableLiveData<List<Item>>()
    }

    fun getAllShelters() { //DB의 모든 수를 가져오는 쿼리.
        shelterList.postValue(shelterDao.getAllShelterData())
    }

    private fun insertShelter(shelter: Item) { //아이템을 넣어준다.
        shelterDao.insertShelterData(shelter)
    }

    fun getUserCount(): Int {
        return shelterDao.getUserCount()
    }

    fun deleteShelter(shelter: Item) {
        shelterDao.deleteShelterData(shelter)
    }

    init {
        loadData()
    }

    fun addRoom(list: List<Item>) { //없으면 그만큼 채우기 위해 넣어준다. 만약 10개가 이미 있어도 중복데이터는 막아서 괜찮다.
        viewModelScope.launch {
            list.forEach {
                CoroutineScope(Dispatchers.IO).launch {
                    insertShelter(it)
                }
            }
        }
    }

    fun loadCtpv(list: List<Item>){ // ctpv가 무엇이 있는지 알아보기위해 메서드 작성.
        var lists = mutableSetOf<String>()
        list.forEach{
            lists.add(it.ctpvNm!!)
        }
        Log.i("ctpv",lists.toString())
    }


    private fun loadData() {
        val service = RetrofitClient.getInstance().create(APIService::class.java).queryShelter(
            "hSF2++mMiQXwGI5XfZmbSDPqorgTy+jVDuSMNwWmA1gY2h2HASedPbnFIz/eEBlxG8O2nv2vIsz7WSGLeIjWzw==",
            1, 1000, "json"
        )
        service.enqueue(object : Callback<ShelterDTO> {
            override fun onResponse(call: Call<ShelterDTO>, response: Response<ShelterDTO>) {
                var result = response.body()?.response?.header?.resultCode // 응답 확인.
                if (result == "0") { // 받아온 아이템 배열
                    totalNum =
                        response.body()?.response!!.body?.totalCount?.toInt()!! // 총 갯수도 파악하기 위해. 변수 세팅.
                    var list: List<Item> =
                        response.body()!!.response?.body?.items?.item!! // 아이템을 받아옴.
                    viewModelScope.launch {
                        CoroutineScope(Dispatchers.IO).launch {
                            shelterList.postValue(list) //모든수가 있어야하는 List
                            shelterFilterList.postValue(list) // 처음에 shelterFilter에 전부 있어야 하기 때문에 넣어줌.
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ShelterDTO>, t: Throwable) {
                Log.i("fail", t.message.toString())
            }
        })
    }
}
