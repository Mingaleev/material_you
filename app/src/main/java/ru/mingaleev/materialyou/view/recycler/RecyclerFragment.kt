package ru.mingaleev.materialyou.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import ru.mingaleev.materialyou.data.Data
import ru.mingaleev.materialyou.data.TYPE_EARTH
import ru.mingaleev.materialyou.data.TYPE_HEADER
import ru.mingaleev.materialyou.data.TYPE_MARS
import ru.mingaleev.materialyou.databinding.FragmentRecyclerBinding

class RecyclerFragment : Fragment() {
    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val data = arrayListOf(
        Pair(Data(0, "Header", type = TYPE_HEADER), false),
        Pair(Data(1, "Earth", type = TYPE_EARTH), false),
        Pair(Data(2, "Earth", type = TYPE_EARTH), false),
        Pair(Data(3, "Earth", type = TYPE_EARTH), false),
        Pair(Data(4, "Earth", type = TYPE_EARTH), false),
        Pair(Data(5, "Earth", type = TYPE_EARTH), false),
        Pair(Data(6, "Mars", "", type = TYPE_MARS), false),
        Pair(Data(7, "Earth", type = TYPE_EARTH), false),
        Pair(Data(8, "Earth", type = TYPE_EARTH), false),
        Pair(Data(9, "Earth", type = TYPE_EARTH), false),
        Pair(Data(10, "Mars", type = TYPE_MARS), false)
    )

    private var isNewList = false

    private lateinit var adapter: RecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerAdapter(data, callbackAdd, callbackRemove)
        binding.recyclerView.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)

        binding.recyclerActivityFAB.setOnClickListener{
            changeAdapterData()
        }
    }

    private val callbackAdd = AddItem {
        data.add(it, Pair(Data(0, "Mars (New)", type = TYPE_MARS), false))
        adapter.setListDataAdd(data, it)
    }
    private val callbackRemove = RemoveItem {
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }

    private fun changeAdapterData() {
        adapter.setListDataForDiffUtil(createItemList(isNewList).map { it }.toMutableList())
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, "Header", type = TYPE_HEADER), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Mars", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Mars", ""), false),
                Pair(Data(5, "Mars", ""), false),
                Pair(Data(6, "Mars", ""), false)
            )
            true -> listOf(
                Pair(Data(0, "Header", type = TYPE_HEADER), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Jupiter", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Neptune", ""), false),
                Pair(Data(5, "Saturn", ""), false),
                Pair(Data(6, "Saturn", ""), false),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}