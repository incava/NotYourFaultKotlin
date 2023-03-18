package com.incava.notyourfaultkotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed
import com.incava.notyourfaultkotlin.databinding.ActivitySplashViewBinding
import kotlin.random.Random

/**
 * 스플래쉬 화면 구현. 따뜻한 말과 위로의 말을 보여주고자 구성.
 */
class SplashViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashViewBinding.inflate(layoutInflater)
            setContentView(binding.root)
        setTextCmt()
        moveMain(3)
    }

    private fun setTextCmt(){ //랜덤으로 텍스트 기입
        val array : Array<String> = resources.getStringArray(R.array.commentAry)
        binding.textView.text = array[Random.nextInt(array.size -1)]
    }

    private fun moveMain(sec: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            //new Intent(현재 context, 이동할 activity)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) //intent 에 명시된 액티비티로 이동
            finish() //현재 액티비티 종료
        }, 1000L * sec) // sec초 정도 딜레이를 준 후 시작
    }


}