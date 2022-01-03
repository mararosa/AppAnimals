package com.estudos.animalsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.estudos.animalsapp.R
import com.estudos.animalsapp.databinding.ItemAnimalBinding
import com.estudos.animalsapp.model.Animal
import com.estudos.animalsapp.util.getProgressDrawable
import com.estudos.animalsapp.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAnimalBinding>(
            inflater,
            R.layout.item_animal,
            parent,
            false
        )
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.binding.animal = animalList[position]
        holder.binding.listener = this
    }

    override fun getItemCount() = animalList.size


    inner class AnimalViewHolder(var binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onClick(view: View) {
        for (animal in animalList) {
            if (view.tag == animal.name) {
                val action = ListFragmentDirections.actionListFragmentToDetailFragment(animal)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }
}