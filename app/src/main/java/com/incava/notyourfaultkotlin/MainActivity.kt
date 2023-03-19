package com.incava.notyourfaultkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.data.ShelterDTO
import com.incava.notyourfaultkotlin.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 뷰페이저로 map과 Query를 보여주기 위한 액티비티.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var fragmentArr = arrayListOf(MapFragment(), QueryFragment(), WantFragment())
    private var tabArr = arrayListOf("Map", "조회", "찜")
    var isLoadData = false // 데이터가로드되었는지 확인.
    var totalNum = Int.MAX_VALUE  //데이터의 갯수를 알 수 있는 변수, 초기에는 무조건 받아야하므로 최대 수로 셋팅.
    var shelterList = ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
        setTabLayout()
        loadData()
    }

    private fun setViewPager() { // 뷰페이저 설정
        viewPagerAdapter = ViewPagerAdapter(this@MainActivity)
        viewPagerAdapter.addAllFragment(fragmentArr)
        binding.pager.apply {
            adapter = viewPagerAdapter
        }
    }

    private fun setTabLayout() { // 탭레이아웃 설정
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArr[position]
        }.attach()
    }

    private fun loadData() {
        val service = RetrofitClient.getInstance().create(APIService::class.java).queryShelter(
            "hSF2++mMiQXwGI5XfZmbSDPqorgTy+jVDuSMNwWmA1gY2h2HASedPbnFIz/eEBlxG8O2nv2vIsz7WSGLeIjWzw==",
            1, 1000, "json"
        )
        service.enqueue(object : Callback<ShelterDTO> {
            override fun onResponse(call: Call<ShelterDTO>, response: Response<ShelterDTO>) {
                var result = response.body()?.response?.header?.resultCode//응답 확인.
                    if (result == "0") {
                        shelterList = (response.body()?.response?.body?.items?.item ?: return) as ArrayList<Item>
                        totalNum = response.body()?.response!!.body?.totalCount?.toInt() ?: -1 // 총 갯수도 파악하기 위해. 변수 세팅.
                        isLoadData = true // 로딩이 다되었다고 알림.
                        binding.pb.visibility = View.GONE
                        binding.pbCmt.visibility = View.GONE
                    }
                Log.i("size", shelterList.size.toString())
            }
            override fun onFailure(call: Call<ShelterDTO>, t: Throwable) {
                Log.i("fail", t.message.toString())
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("destroy", "destroy")
        finish() // 요즘은 destroy해도 바로 안꺼짐
    }

}