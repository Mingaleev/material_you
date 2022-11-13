package ru.mingaleev.materialyou.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenEarthBinding
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenHeaderBinding
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenMarsBinding

class RecyclerAdapter(private val listData: List<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding = FragmentRecyclerItenEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding = FragmentRecyclerItenMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            else -> {
                val binding = FragmentRecyclerItenHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).bind(listData[position])
            }
            TYPE_MARS -> {
                (holder as MarsViewHolder).bind(listData[position])
            }
            else -> {
                (holder as HeaderViewHolder).bind(listData[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MarsViewHolder(private val binding: FragmentRecyclerItenMarsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class EarthViewHolder(private val binding: FragmentRecyclerItenEarthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class HeaderViewHolder(private val binding: FragmentRecyclerItenHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.name.text = data.name
        }
    }
}