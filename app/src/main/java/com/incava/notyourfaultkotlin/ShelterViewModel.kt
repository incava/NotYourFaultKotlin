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
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Room을 관리하기 위한 ViewModel. application의 권한을 받기 위해 AndroidViewModel을 사용.
 */
class ShelterViewModel(application: Application) : AndroidViewModel(application) {
    private val shelterDao = ShelterDatabase.getInstance(application)!!.shelterDao()
    var totalNum = Int.MAX_VALUE // 데이터의 갯수를 알 수 있는 변수, 초기에는 무조건 받아야하므로 최대 수로 셋팅.
    val shelterList: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }
    val shelterFilterList: MutableLiveData<List<Item>> by lazy { //
        MutableLiveData<List<Item>>()
    }
    val favoriteList: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }

    var roomNameSet = mutableSetOf<String>() // room에 있는 이름들을 관리하는 Set

    init {
        loadData()
    }

    suspend fun getAllShelters(){ //DB의 모든 수를 가져오는 쿼리.
        roomNameSet =  withContext(Dispatchers.IO){
             shelterDao.getAllShelterNameData().toMutableSet()
        }
    }

    fun insertShelter(shelter: Item) { //아이템을 넣어준다.
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("insert",shelter.toString())
            shelterDao.insertShelterData(shelter)
            roomNameSet.add(shelter.fcltNm)
        }
    }

    fun getUserCount(): Int {
        return shelterDao.getUserCount()
    }

    fun deleteShelter(shelter: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            shelterDao.deleteShelterData(shelter)
            roomNameSet.remove(shelter.fcltNm)
        }
    }

    fun getFilterArray(region: String, gender: String) { // filter를 통해 filterList에 전달.
        var list: List<Item> = shelterList.value ?: return
        shelterFilterList.postValue(genderFilter(gender,regionFilter(region,list)))
    }


    //지역을 거를 수 있는 filter 함수인 변수.
    val regionFilter : (String, List<Item>) -> List<Item> = { region, list -> //람다식으로 변수화 시켰음.
        if (region == "전체" || region == "") list //만약 빈값 또는 전체가 값으로 오면 받은 그대로를 보냄.
        else {
            val filterList = mutableListOf<Item>() //저장할 값.
            list.forEach {
                if (it.ctpvNm == region) filterList.add(it) //같은 값으로 보여줌.
            }
            filterList
        }
    }

    //성별을 filter 할 수 있는 함수인 변수.
    val genderFilter : (String, List<Item>) -> List<Item> = { gender, list-> //공통은 무조건 들어가도록 하였음.
        if(gender == "전체" || gender == "") list//만약 빈값 또는 전체가 값으로 오면 받은 그대로를 보냄.
        else{
            val filterList = mutableListOf<Item>()
            list.forEach{
                if (it.etrTrgtCn == "공통" || it.etrTrgtCn == gender) filterList.add(it)
            }
            filterList
        }
    }
    fun loadCtpv(list: List<Item>) { // ctpv가 무엇이 있는지 알아보기위해 메서드 작성.
        var lists = mutableSetOf<String>()
        list.forEach {
            lists.add(it.ctpvNm!!)
        }
        Log.i("ctpv", lists.toString())
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
                            getAllShelters() // Room에서 사용하는 제목들 가져오기.
                            Log.i("getAllShelters",getAllShelters().toString())
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
