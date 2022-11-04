package ru.mingaleev.materialyou.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.transition.Explode
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import ru.mingaleev.materialyou.R
import ru.mingaleev.materialyou.databinding.FragmentAnimationBinding

class AnimationFragment : Fragment() {

    private var _binding: FragmentAnimationBinding? = null
    private val binding get() = _binding!!

    private var flagVisibilityTextView = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flagVisibilityTextView = !flagVisibilityTextView

        binding.recyclerView.adapter = Adapter()
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_animation_explode_list_item, parent, false) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                val myAutoTransition = TransitionSet()
                val explode = Explode()

                myAutoTransition.addTransition(explode)
                myAutoTransition.duration = 1000L

                TransitionManager.beginDelayedTransition(binding.transitionContainer, myAutoTransition)
                binding.recyclerView.adapter = null
            }
        }

        override fun getItemCount(): Int {
            return 32
        }

        inner class MyViewHolder(view: View) : ViewHolder(view)

    }
}