package com.incava.notyourfaultkotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.incava.notyourfaultkotlin.data.ShelterDTO
//import androidx.lifecycle.lifecycleScope
import com.incava.notyourfaultkotlin.databinding.FragmentMapBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = RetrofitClient.getInstance().create(APIService::class.java).queryShelter(
                "hSF2++mMiQXwGI5XfZmbSDPqorgTy+jVDuSMNwWmA1gY2h2HASedPbnFIz/eEBlxG8O2nv2vIsz7WSGLeIjWzw==",
        1, 1000, "json")
        service.enqueue(object : Callback<ShelterDTO>{
            override fun onResponse(call: Call<ShelterDTO>, response: Response<ShelterDTO>) {
                var result = response.body()?.response
                if (result != null) {
                    val list = result.body?.items?.item
                    var a = 0
                    list?.forEach {
                        Log.i("items$a", it.toString())
                        a++
                    }

                }
            }

            override fun onFailure(call: Call<ShelterDTO>, t: Throwable) {
                Log.i("fail",t.message.toString())
            }

        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}