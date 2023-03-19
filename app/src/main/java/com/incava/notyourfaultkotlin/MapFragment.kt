package com.incava.notyourfaultkotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.incava.notyourfaultkotlin.databinding.FragmentMapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.create

class MapFragment : Fragment() {

    private var _binding : FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            val service = RetrofitClient.getInstance().create(APIService::class.java)
            val result = withContext(Dispatchers.IO) {
                service.queryShelter("hSF2%2B%2BmMiQXwGI5XfZmbSDPqorgTy%2BjVDuSMNwWmA1gY2h2HASedPbnFIz%2FeEBlxG8O2nv2vIsz7WSGLeIjWzw%3D%3D",
                    1,10,"json")
            }
            Log.i("result", result.body().toString())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}