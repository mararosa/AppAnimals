package com.estudos.animalsapp.di

import com.estudos.animalsapp.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalApiService)
}