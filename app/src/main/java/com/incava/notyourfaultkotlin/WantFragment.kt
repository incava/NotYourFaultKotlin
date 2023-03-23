package com.incava.notyourfaultkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.databinding.FragmentWantBinding

class WantFragment : Fragment() {
    private var _binding: FragmentWantBinding? = null
    private val binding get() = _binding!!
    private val shelterAdapter: ShelterAdapter by lazy {
        ShelterAdapter(shelterViewModel.roomNameSet)
    }
    private val shelterViewModel: ShelterViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachRcvAdapter()
        initObserver()
        initClickListener()
    }

    private fun attachRcvAdapter(){
       // adapter 달아 주기..
        binding.rcv.adapter = shelterAdapter
    }

    override fun onResume() {
        super.onResume()
        shelterViewModel.getAllShelters() //뷰가 보이면 요청.
    }


    private fun initObserver() { //옵져버 붙여주는 메서드
        shelterViewModel.favoriteList.observe(viewLifecycleOwner) {
            shelterAdapter.setItem(it) // 바뀐 부분에 대해 아이템을 넣어 준다.
            shelterAdapter.notifyDataSetChanged()
        }
    }

    private fun initClickListener() {
        //어댑터의 하트를 클릭시 처리하는 메서드.
        shelterAdapter.listItemClick(object : ShelterAdapter.SetOnClickListenerInterface {
            override fun listItemClickListener(item: Item, select: Boolean) {
                if (select) shelterViewModel.deleteShelter(item)
                else shelterViewModel.insertShelter(item)
            }
        })

        binding.fab.setOnClickListener {
            //스크롤을 올릴 때 smooth하게 올려주는 기능.
            binding.rcv.smoothScrollToPosition(0)
        }
    }


}