package com.estudos.animalsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.estudos.animalsapp.model.Animal

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        getAnimals()
    }

    private fun getAnimals() {
        val a1 = Animal(name = "alligator")
        val a2 = Animal(name = "bee")
        val a3 = Animal(name = "cat")
        val a4 = Animal(name = "dog")
        val a5 = Animal(name = "elephant")
        val a6 = Animal(name = "flaming")

        val animalLits = arrayListOf(a1, a2, a3, a4, a5, a6)

        animals.value = animalLits
        loadError.value = false
        loading.value = false
    }
}