package com.incava.notyourfaultkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.incava.notyourfaultkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }




    override fun onDestroy() {
        super.onDestroy()
        Log.i("destroy","destroy")
        finish() // 요즘은 destroy해도 바로 안꺼짐
    }

}