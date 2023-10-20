package com.incava.notyourfaultkotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.databinding.FragmentDetailBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import java.util.Locale

class DetailFragment : Fragment(), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    val args: DetailFragmentArgs by navArgs()
    val selectShelterInfo by lazy {
        Gson().fromJson(args.shelterDetailInfo, Item::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setItemInfo()
        attachListener() //리스너 연결.
        binding.map.getFragment<MapFragment>().getMapAsync(this)
    }

    private fun setItemInfo() {
        binding.apply {
            selectShelterInfo.let { item ->
                tvLimitDay.text = item.etrPrdCn
                tvNum.text = item.cpctCnt
                tvTarget.text = item.etrTrgtCn
                tvOper.text = item.operModeCn
                tvSub.text = item.nrbSbwNm
                tvBus.text = item.nrbBusStnNm
                tvTel.text = PhoneNumberUtils.formatNumber(
                    item.rprsTelno,
                    Locale.getDefault().country
                ) //지역에 맞게 하이픈 설정.

                tvFax.text = PhoneNumberUtils.formatNumber(item.fxno, Locale.getDefault().country)
                tvHmpg.text = item.hmpgAddr
                tvAddr.text = item.lotnoAddr
                setToolbar(item.fcltNm)
            }

        }
    }

    private fun setToolbar(title: String?) {
        // 앱바 적용.
        binding.toolbar.apply {
            setupWithNavController(findNavController())
            this.title = title
        }
    }


    override fun onMapReady(map: NaverMap) {
        naverMap = map
        settingMark(naverMap)
    }

    private fun settingMark(naverMap: NaverMap) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            val cameraUpdate = CameraUpdate.scrollTo(
                LatLng(
                    selectShelterInfo.lat?.toDouble() ?: 37.0,
                    selectShelterInfo.lot?.toDouble() ?: 127.0
                )
            ) //카메라 움직임.
                .animate(CameraAnimation.Fly) //애니메이션 추가.
            naverMap.moveCamera(cameraUpdate)
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        } else {
            Log.i(//로그값 출력
                "noGrant",
                requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .toString() + ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
            naverMap.locationTrackingMode = LocationTrackingMode.None
        }
        Log.i("shelter",selectShelterInfo.toString())
        selectShelterInfo.let { item ->
            //Log.i("items","${item.lot} ${item.lat}")
            if (item.lat?.isEmpty() == true || item.lot?.isEmpty() == true)
                return@let // 만약 좌표가 없다면 마커 그리기 x
            val marker = Marker()
            marker.position =
                LatLng(item.lat?.toDouble() ?: 37.0, item.lot?.toDouble() ?: 127.0) //마커 위도 경도 넣기.
            marker.captionText = item.fcltNm  // 앱바의 제목과 일치하므로 넣어줌.
            marker.map = naverMap // 마커 생성.
        }
        Log.i("end", "end완료")
    }

    fun attachListener() {
        binding.apply {
            tvHmpg.setOnClickListener{ hmpgClick() }
            tvTel.setOnClickListener{ telClick() }
        }

    }

    fun hmpgClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.tvHmpg.text.toString()))
        requireActivity().startActivity(intent)
    }

    fun telClick() {
        //전화번호 클릭시 전화할 수 있도록
        val tt = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + binding.tvTel.text.toString()))
        startActivity(tt)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}