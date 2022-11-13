package ru.mingaleev.materialyou.view.recycler

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mingaleev.materialyou.data.Data
import ru.mingaleev.materialyou.data.TYPE_EARTH
import ru.mingaleev.materialyou.data.TYPE_MARS
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenEarthBinding
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenHeaderBinding
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenMarsBinding

class RecyclerAdapter(private var listData: List<Data>, val callbackAdd: AddItem, val callbackRemove: RemoveItem) : RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    fun setListDataAdd (listDateNew: List<Data>, position: Int) {
        listData = listDateNew
        notifyItemInserted(position)
    }
    fun setListDataRemove (listDateNew: List<Data>, position: Int) {
        listData = listDateNew
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MarsViewHolder(private val binding: FragmentRecyclerItenMarsBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(adapterPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(adapterPosition)
            }
        }
    }

    class EarthViewHolder(private val binding: FragmentRecyclerItenEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class HeaderViewHolder(private val binding: FragmentRecyclerItenHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    abstract class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Data)
    }
}