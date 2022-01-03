package com.estudos.animalsapp.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.estudos.animalsapp.R
import com.estudos.animalsapp.databinding.FragmentDetailBinding
import com.estudos.animalsapp.model.Animal
import com.estudos.animalsapp.model.AnimalPalette
import com.estudos.animalsapp.util.getProgressDrawable
import com.estudos.animalsapp.util.loadImage


class DetailFragment : Fragment() {

    private lateinit var dataBinding: FragmentDetailBinding

    var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animalArgs
        }


        setupView()
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
                           dataBinding.palette = AnimalPalette(intColor)
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun setupView() {
        dataBinding.animal = animal

        animal?.imageUrl?.let {
            setBackgroundColor(it)
        }

    }
}