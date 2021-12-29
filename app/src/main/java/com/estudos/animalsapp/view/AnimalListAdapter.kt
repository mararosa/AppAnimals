package com.estudos.animalsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estudos.animalsapp.R
import com.estudos.animalsapp.databinding.ItemAnimalBinding
import com.estudos.animalsapp.model.Animal

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = ItemAnimalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        with(holder) {
            with(animalList[position]) {
                binding.animalName.text = name
            }
        }
    }

    override fun getItemCount() = animalList.size

    inner class AnimalViewHolder(var binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)
}