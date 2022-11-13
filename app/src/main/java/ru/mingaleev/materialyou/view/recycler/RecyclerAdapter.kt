package ru.mingaleev.materialyou.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mingaleev.materialyou.data.Data
import ru.mingaleev.materialyou.data.TYPE_EARTH
import ru.mingaleev.materialyou.data.TYPE_MARS
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenEarthBinding
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenHeaderBinding
import ru.mingaleev.materialyou.databinding.FragmentRecyclerItenMarsBinding

class RecyclerAdapter(
    private var listData: MutableList<Data>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem
) : RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    fun setListDataAdd(listDateNew: MutableList<Data>, position: Int) {
        listData = listDateNew
        notifyItemInserted(position)
    }

    fun setListDataRemove(listDateNew: MutableList<Data>, position: Int) {
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
                callbackAdd.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }

            binding.moveItemUp.setOnClickListener {
                if (layoutPosition > 1) {
                    listData.removeAt(layoutPosition).apply {
                        listData.add(layoutPosition - 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition - 1)
                }
            }
            binding.moveItemDown.setOnClickListener {
                if (layoutPosition < listData.size - 1) {
                    listData.removeAt(layoutPosition).apply {
                        listData.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
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