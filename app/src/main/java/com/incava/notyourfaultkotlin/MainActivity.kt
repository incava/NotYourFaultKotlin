package com.incava.notyourfaultkotlin

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.incava.notyourfaultkotlin.database.ShelterDAO
import com.incava.notyourfaultkotlin.databinding.ActivityMainBinding

/**
 * 뷰페이저로 map과 Query를 보여주기 위한 액티비티.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
    }
    private fun setNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController) //바텀네비게이션과 프래그먼트를 연결시켜 select시 이동하도록 구현.
        //menu의 아이템 id와 navigation id는 같아야 자동으로 연결된다.
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("destroy", "destroy")
        finish() // 요즘은 destroy해도 바로 안꺼짐
    }


}