package com.example.airlinesapp.ui.airlineslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.airlinesapp.data.pojo.Airline
import com.example.airlinesapp.databinding.ItemAirlinesRvBinding

class AirlinesListAdapter:ListAdapter<Airline,AirlinesListAdapter.ViewHolder>(AirlinesComparator()) {

    var onAirlineClickListener:SetOnAirlineClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAirlinesRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemAirlinesRvBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Airline) {
            binding.apply {
                airlineNameTv.text = item.name
                if (onAirlineClickListener != null) {
                    itemView.setOnClickListener {
                        onAirlineClickListener!!.onClick(item)
                    }
                }
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface SetOnAirlineClickListener{
        fun onClick(airline: Airline)
    }

}

class AirlinesComparator : DiffUtil.ItemCallback<Airline>() {
    override fun areItemsTheSame(oldItem: Airline, newItem: Airline) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Airline, newItem: Airline) =
        oldItem == newItem
}