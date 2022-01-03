package com.estudos.animalsapp.di

import android.app.Application
import com.estudos.animalsapp.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton
    fun provideSharePreferences(app: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(app)
    }
}