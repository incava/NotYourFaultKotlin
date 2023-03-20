package com.incava.notyourfaultkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
        setTabLayout()

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

    override fun onDestroy() {
        super.onDestroy()
        Log.i("destroy", "destroy")
        finish() // 요즘은 destroy해도 바로 안꺼짐
    }

}