package com.incava.notyourfaultkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.databinding.ShelterItemBinding

/**
 * 조회한 아이템을 보여주는 리사이클러뷰 어댑터
 */
class ShelterAdapter(private val items : ArrayList<Item>) : Adapter<ShelterAdapter.ShelterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelterViewHolder {
        val binding = ShelterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShelterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShelterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ShelterViewHolder(private val binding : ShelterItemBinding) : ViewHolder(binding.root){
        fun bind(item : Item){
            binding.cpctCnt.text = item.cpctCnt
            binding.etrPrdCn.text = item.etrPrdCn
            binding.etrTrgtCn.text = item.etrTrgtCn
            binding.fxno.text = item.fxno
            binding.rprsTelno.text = item.rprsTelno
            binding.fcltNm.text = item.fcltNm
            binding.lotnoAddr.text = item.lotnoAddr
        }
    }
}