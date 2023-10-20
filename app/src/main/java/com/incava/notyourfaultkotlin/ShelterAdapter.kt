package com.incava.notyourfaultkotlin

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.incava.notyourfaultkotlin.data.Item
import com.incava.notyourfaultkotlin.databinding.ShelterItemBinding

/**
 * 조회한 아이템을 보여주는 리사이클러뷰 어댑터
 */
class ShelterAdapter(_nameSet: MutableSet<String>) : Adapter<ShelterAdapter.ShelterViewHolder>() {
    private var items = mutableListOf<Item>()

    private var nameSet = _nameSet

    interface SetOnClickListenerInterface {
        fun listItemClickListener(item: Item, select: Boolean)
        fun listItemClickListener(item: Item)
    }

    private var onClickListener: SetOnClickListenerInterface? = null

    fun listItemClick(mClick: SetOnClickListenerInterface) {
        this.onClickListener = mClick
    }

    fun setItem(itemList: List<Item>) {
        items.clear()
        items.addAll(itemList) // filter라서 모두 삭제후  다시 아이템을 넣고 변경.
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelterViewHolder {
        val binding = ShelterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShelterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShelterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ShelterViewHolder(private val binding: ShelterItemBinding) :
        ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                cpctCnt.text = item.cpctCnt
                etrPrdCn.text = item.etrPrdCn
                etrTrgtCn.text = item.etrTrgtCn.toString()
                fxno.text = item.fxno
                rprsTelno.text = item.rprsTelno
                fcltNm.text = item.fcltNm
                lotnoAddr.text = item.lotnoAddr
            }
            //아이템 이름이 최초 실행시에 Room안에 있다면 미리 찜하기에 빨간불이 들어오도록 한다.
            binding.btnFavorite.isSelected = nameSet.contains(item.fcltNm)


            // 아이템 클릭시 리스너 발생
            if (adapterPosition != RecyclerView.NO_POSITION) {//아이템이 존재하면 실행.
                binding.btnFavorite.setOnClickListener {
                    onClickListener?.listItemClickListener(item = item, it.isSelected)

                    if (it.isSelected) nameSet.add(item.fcltNm) //만약 처음 누르는 경우면 nameSet에 저장.
                    else nameSet.remove(item.fcltNm) //아니라면 제거

                    it.isSelected = !(it.isSelected) //셀렉트를 반대로 대입.
                    Log.i("selected", it.isSelected.toString())
                }
                //리스트 클릭 시 리스너
                binding.root.setOnClickListener {
                    onClickListener?.listItemClickListener(item = item)
                }
            }
        }
    }
}