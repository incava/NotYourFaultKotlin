package com.incava.notyourfaultkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.incava.notyourfaultkotlin.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker

/**
 * 권한을 요구 하고, 요구가 없다면 맵 Fragment
 */

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val shelterViewModel: ShelterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(naverMap: NaverMap) {
       settingMark(naverMap)
    }

    private fun settingMark(naverMap: NaverMap) {
        if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            val cameraUpdate = CameraUpdate.scrollTo(getCurrentPosition()) //카메라 움직임.
                .animate(CameraAnimation.Fly) //애니메이션 추가.
            naverMap.moveCamera(cameraUpdate)
        }
        else{
            Log.i("noGrant",
                requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION).toString()+ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)
            )
        }
        shelterViewModel.shelterList.observe(viewLifecycleOwner) {
            it.forEach { item ->
                val marker = Marker()
                marker.position =
                    LatLng(item.lat!!.toDouble(), item.lot!!.toDouble()) //마커 위도 경도 넣기.
                marker.captionText = item.fcltNm // 앱바의 제목과 일치하므로 넣어줌.
                marker.map = naverMap // 마커 생성.
            }
            Log.i("end", "end완료")
        }
    }

    private fun getCurrentPosition(): LatLng {
        var latlng = LatLng(0.0, 0.0)
        val manager =
            requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                latlng = LatLng(location.latitude, location.longitude)
                Log.i("latlng", "${location.latitude} ${location.longitude}")
            }
        }
        Log.i("latlng", latlng.toString())
        return latlng
    }

    private var permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            binding.map.getFragment<MapFragment>().getMapAsync(this)
        }
}