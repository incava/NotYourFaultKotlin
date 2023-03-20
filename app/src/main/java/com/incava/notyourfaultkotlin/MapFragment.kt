package com.incava.notyourfaultkotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val shelterViewModel: ShelterViewModel by activityViewModels()

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
        binding.mapView.getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(naverMap: NaverMap) {
        settingMark(naverMap)
        shelterViewModel.loadData()
    }

    private fun settingMark(naverMap: NaverMap) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.541, 126.986)) //카메라 움직임.
            .animate(CameraAnimation.Fly) //애니메이션 추가.
        naverMap.moveCamera(cameraUpdate)
        shelterViewModel.shelterList.observe(viewLifecycleOwner) {
            it.forEach { item ->
                val marker = Marker()
                marker.position =
                    LatLng(item.lat!!.toDouble(), item.lot!!.toDouble()) //마커 위도 경도 넣기.
                marker.captionText = "인기다." // 앱바의 제목과 일치하므로 넣어줌.
                marker.map = naverMap // 마커 생성.
            }
            Log.i("end","end완료")
        }
    }
}