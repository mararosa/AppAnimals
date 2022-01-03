package com.estudos.animalsapp.di

import com.estudos.animalsapp.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {

    fun inject(viewModel: ListViewModel)
}