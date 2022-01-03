package com.estudos.animalsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.estudos.animalsapp.R
import com.estudos.animalsapp.databinding.ItemAnimalBinding
import com.estudos.animalsapp.model.Animal
import com.estudos.animalsapp.util.getProgressDrawable
import com.estudos.animalsapp.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_animal, parent, false)
//        return AnimalViewHolder(view)
        val binding = ItemAnimalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        with(holder) {
            with(animalList[position]) {
//                view.animalName.text = name
//                view.animalImage.loadImage(imageUrl, getProgressDrawable(holder.view.context))

                //TODO como pegar context usando o binding, nao consegui e ai voltei pro synthetic
                binding.animalName.text = name
                binding.animalImage.loadImage(imageUrl, getProgressDrawable(binding.root.context))
                binding.animalItemLayout.setOnClickListener {
                    val action = ListFragmentDirections.actionListFragmentToDetailFragment(this)
                    Navigation.findNavController(holder.itemView).navigate(action)
                }
            }
        }
    }

    override fun getItemCount() = animalList.size


//    inner class AnimalViewHolder(var view: View) :
//        RecyclerView.ViewHolder(view)

    inner class AnimalViewHolder(var binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)
}