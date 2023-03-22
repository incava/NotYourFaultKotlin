package com.incava.notyourfaultkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.incava.notyourfaultkotlin.databinding.FragmentQueryBinding


class QueryFragment : Fragment() {

    private var _binding : FragmentQueryBinding? = null
    private val binding get() = _binding!!
    private val shelterViewModel: ShelterViewModel by activityViewModels()
    private val shelterAdapter : ShelterAdapter by lazy {
        ShelterAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQueryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcv.apply {
            adapter = shelterAdapter
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        }
        val regions = resources.getStringArray(R.array.regionAry)
        binding.actvRegion.setAdapter(ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,
            regions))
        initObserver()
    }

    private fun initObserver(){
        shelterViewModel.shelterFilterList.observe(viewLifecycleOwner){
            shelterAdapter.setItem(it) // 바뀐 부분에 대해 아이템을 넣어 준다.
        }
    }



}