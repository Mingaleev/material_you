package ru.mingaleev.materialyou.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        Data( "Header", type = TYPE_HEADER),
        Data( "Earth", type = TYPE_EARTH),
        Data( "Earth", type = TYPE_EARTH),
        Data( "Earth", type = TYPE_EARTH),
        Data( "Earth", type = TYPE_EARTH),
        Data( "Earth", type = TYPE_EARTH),
        Data("Mars", "", type = TYPE_MARS),
        Data( "Earth", type = TYPE_EARTH),
        Data( "Earth", type = TYPE_EARTH),
        Data( "Earth", type = TYPE_EARTH),
        Data("Mars", type = TYPE_MARS)
    )

    lateinit var adapter: RecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerAdapter(data, callbackAdd, callbackRemove)
        binding.recyclerView.adapter = adapter
    }

    private val callbackAdd = AddItem {
        data.add(it, Data("Mars (New)", type = TYPE_MARS))
        adapter.setListDataAdd(data,it)
    }
    private val callbackRemove = RemoveItem {
        data.removeAt(it)
        adapter.setListDataRemove(data,it)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}