package com.estudos.animalsapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.estudos.animalsapp.di.AppModule
import com.estudos.animalsapp.di.DaggerViewModelComponent
import com.estudos.animalsapp.model.Animal
import com.estudos.animalsapp.model.AnimalApiService
import com.estudos.animalsapp.model.ApiKey
import com.estudos.animalsapp.util.SharedPreferencesHelper
import com.estudos.animalsapp.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    private val key = "test key"

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

    @Test
    fun getAnimalsSuccess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val animal = Animal("dog", null, null, null, null, null, null)
        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)
        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.loadInfo()

        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getAnimalsFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val testSingle = Single.error<List<Animal>>(Throwable())
        val keySingle = Single.just(ApiKey("Ok", key))

        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        listViewModel.loadInfo()

        Assert.assertEquals(null, listViewModel.animals.value)
        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
    }

    @Test
    fun getApiKeySuccess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val response = ApiKey("ok", key)
        val keySingle = Single.just(response)

        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        val animal = Animal("dog", null, null, null, null, null, null)
        val animalList = listOf(animal)
        val testSingle = Single.just(animalList)

        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.loadInfo()

        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getKeyFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle = Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        listViewModel.loadInfo()

        Assert.assertEquals(null, listViewModel.animals.value)
        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
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