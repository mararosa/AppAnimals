package com.estudos.animalsapp

import com.estudos.animalsapp.di.ApiModule
import com.estudos.animalsapp.model.AnimalApiService

class ApiModuleTest(val mockService: AnimalApiService) : ApiModule() {

    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }
}