package com.incava.notyourfaultkotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.databinding.FragmentQueryBinding


class QueryFragment : Fragment() {


    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!
    private val shelterViewModel: ShelterViewModel by activityViewModels()
    private val shelterAdapter: ShelterAdapter by lazy {
        ShelterAdapter(shelterViewModel.roomNameSet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQueryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        binding.rcv.apply {
            adapter = shelterAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
        initObserver()
        //attachSpinner()
    }

    private fun initClickListener(){
        //어댑터에서 클릭 이벤트가 오면 처리할 메서드 구현.
        shelterAdapter.listItemClick(object : ShelterAdapter.SetOnClickListenerInterface{
            override fun listItemClickListener(item: Item, select: Boolean) {
                if(select) shelterViewModel.deleteShelter(item)
                else shelterViewModel.insertShelter(item)
            }

        })
        //버튼 클릭시,조회할 값을 주고 livedata변경.
        binding.btnQuery.setOnClickListener {
            Log.i("region",binding.actvRegion.text.toString())
            Log.i("gender",binding.actvGender.text.toString())
            shelterViewModel.getFilterArray(
                binding.actvRegion.text.toString(),
                binding.actvGender.text.toString()
            )
        }
    }


    private fun initObserver() { //옵져버 붙여주는 메서드
        shelterViewModel.shelterFilterList.observe(viewLifecycleOwner) {
            shelterAdapter.setItem(it) // 바뀐 부분에 대해 아이템을 넣어 준다.
            shelterAdapter.notifyDataSetChanged()
        }
    }

    private fun attachSpinner() { // spinner에 콤보박스를 붙여주는 메서드.
        binding.actvRegion.setAdapter(
            ArrayAdapter(
                requireContext(), android.R.layout.simple_dropdown_item_1line,
                resources.getStringArray(R.array.regionAry)
            )
        ) //지역 String Array Spinner에 붙여준다.
        binding.actvGender.setAdapter(
            ArrayAdapter(
                requireContext(), android.R.layout.simple_dropdown_item_1line,
                resources.getStringArray(R.array.genderAry)
            )
        ) //지역 String Array Spinner에 붙여준다.
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ingiState","onDestroy")
    }

    override fun onStart() {
        super.onStart()
        Log.i("ingiState","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ingiState","onResume")
        attachSpinner()
    }

    override fun onPause() {
        super.onPause()
        Log.i("ingiState","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ingiState","onStop")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("ingiState","onDetach")
    }


}