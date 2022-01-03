package com.estudos.animalsapp.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.estudos.animalsapp.R
import com.estudos.animalsapp.databinding.FragmentDetailBinding
import com.estudos.animalsapp.model.Animal
import com.estudos.animalsapp.util.getProgressDrawable
import com.estudos.animalsapp.util.loadImage
import kotlinx.android.synthetic.main.item_animal.*


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animalArgs
        }

        setupView()
        animal?.imageUrl?.let {
            setBackgroundColor(it)
        }
    }

    private fun setBackgroundColor(imageUrl: String) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate() { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            binding.animalDetailLayout.setBackgroundColor(intColor)
                        }
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun setupView() {
        context?.let { context ->
            with(binding) {
                animalImage.loadImage(animal?.imageUrl, getProgressDrawable(context))
                animalName.text = animal?.name
                animalLocation.text = animal?.location
                animalLifeSpan.text = animal?.lifeSpan
                animalDiet.text = animal?.diet
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}