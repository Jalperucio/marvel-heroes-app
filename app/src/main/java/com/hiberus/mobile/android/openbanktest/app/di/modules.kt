package com.hiberus.mobile.android.openbanktest.app.di

import com.hiberus.mobile.android.openbanktest.app.UiThread
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorBundleBuilder
import com.hiberus.mobile.android.openbanktest.app.marvel.master.MarvelListAdapter
import com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel.MarvelErrorBundleBuilder
import com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel.MarvelNavigationViewModel
import com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel.MarvelViewModel
import com.hiberus.mobile.android.openbanktest.data.marvel.repository.MarvelDataRepository
import com.hiberus.mobile.android.openbanktest.data.marvel.source.MarvelDataStore
import com.hiberus.mobile.android.openbanktest.data.marvel.source.MarvelDataStoreFactory
import com.hiberus.mobile.android.openbanktest.datasources.marvel.memory.MarvelMemoryImpl
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.MarvelRemoteImpl
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.MarvelServiceFactory
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.mapper.MarvelHeroResourceItemResponseMapper
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.mapper.MarvelHeroResourceResponseMapper
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.mapper.MarvelHeroResponseMapper
import com.hiberus.mobile.android.openbanktest.datasources.memory.MarvelMemory
import com.hiberus.mobile.android.openbanktest.domain.executor.JobExecutor
import com.hiberus.mobile.android.openbanktest.domain.executor.PostExecutionThread
import com.hiberus.mobile.android.openbanktest.domain.executor.ThreadExecutor
import com.hiberus.mobile.android.openbanktest.domain.marvel.interactor.GetMarvelHeroes
import com.hiberus.mobile.android.openbanktest.domain.marvel.repository.MarvelRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * This file is where all Koin modules are defined.
 */
val applicationModule = module(override = true) {

    factory { MarvelHeroResourceItemResponseMapper() }
    factory { MarvelHeroResourceResponseMapper(get()) }
    factory { MarvelHeroResponseMapper(get()) }

    single<ThreadExecutor> { JobExecutor() }
    single<PostExecutionThread> { UiThread() }

    // IMPORTANT: Named qualifiers must be unique inside the module
    factory<MarvelDataStore>(named("remoteDataStore")) { MarvelRemoteImpl(get(), get()) }
    factory<MarvelDataStore>(named("memoryDataStore")) { MarvelMemoryImpl(get()) }
    factory { MarvelDataStoreFactory(get(named("memoryDataStore")), get(named("remoteDataStore"))) }

    factory { MarvelServiceFactory.makeService() }

    factory { MarvelMemory() }

    factory<MarvelRepository> { MarvelDataRepository(get()) }
}

val splashModule = module(override = true) {}

val mainModule = module(override = true) {
    viewModel { MarvelViewModel(get(), get(named("mainErrorBundleBuilder"))) }
}

val marvelModule = module(override = true) {
    factory { MarvelListAdapter(androidContext()) }
    factory { GetMarvelHeroes(get(), get(), get()) }
    factory<ErrorBundleBuilder>(named("marvelErrorBundleBuilder")) { MarvelErrorBundleBuilder() }
    viewModel { MarvelViewModel(get(), get(named("marvelErrorBundleBuilder"))) }
    viewModel { MarvelNavigationViewModel() }
}