package com.estudos.animalsapp

import android.app.Application
import com.estudos.animalsapp.di.PrefsModule
import com.estudos.animalsapp.util.SharedPreferencesHelper

class PrefsModelTest(val mockPrefs: SharedPreferencesHelper): PrefsModule() {

    override fun provideSharePreferences(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}