package com.estudos.animalsapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.estudos.animalsapp.di.AppModule
import com.estudos.animalsapp.di.DaggerViewModelComponent
import com.estudos.animalsapp.model.AnimalApiService
import com.estudos.animalsapp.util.SharedPreferencesHelper
import com.estudos.animalsapp.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.Executor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalApiService: AnimalApiService

    @Mock
    lateinit var prefs: SharedPreferencesHelper

    val application = Mockito.mock(Application::class.java)

    var listViewModel = ListViewModel(application, true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalApiService))
            .prefsModule(PrefsModelTest(prefs))
            .build()
            .inject(listViewModel)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        //Create Threads return immediate when call is being made
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }

    }
}