package com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.AppAction.GET_MARVEL_HEROES
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorBundleBuilder
import com.hiberus.mobile.android.openbanktest.app.common.model.ResourceState
import com.hiberus.mobile.android.openbanktest.app.common.model.ResourceState.Error
import com.hiberus.mobile.android.openbanktest.app.common.model.ResourceState.Loading
import com.hiberus.mobile.android.openbanktest.app.common.model.ResourceState.Success
import com.hiberus.mobile.android.openbanktest.app.common.viewmodel.CommonEventsViewModel
import com.hiberus.mobile.android.openbanktest.app.common.viewmodel.SingleLiveEvent
import com.hiberus.mobile.android.openbanktest.domain.marvel.interactor.GetMarvelHeroes
import com.hiberus.mobile.android.openbanktest.domain.marvel.interactor.GetMarvelHeroes.GetMarvelHeroesParams
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import io.reactivex.rxjava3.disposables.Disposable

typealias MarvelState = ResourceState<List<MarvelHero>>

class MarvelViewModel(
    private val getMarvelHeroesUseCase: GetMarvelHeroes,
    private val errorBundleBuilder: ErrorBundleBuilder
) : CommonEventsViewModel() {

    private val marvelLiveData: SingleLiveEvent<MarvelState> = SingleLiveEvent()
    private val selectedMarvelHeroLiveData: MutableLiveData<MarvelHero> = MutableLiveData()
    private var marvelHeroes: MutableList<MarvelHero> = arrayListOf()
    private var disposable: Disposable? = null

    var offset = 0

    companion object {

        private const val pageSize = 15
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getMarvelLiveData() = marvelLiveData

    fun getMarvelHeroLiveData() = selectedMarvelHeroLiveData

    fun select(position: Int) {
        // Store selected item for details view
        selectedMarvelHeroLiveData.value = marvelHeroes[position]
    }

    fun fetchMarvelHeroes(forceRemote: Boolean = false) {
        // Do NOT use postValue(), since it will update the value asynchronously. Therefore, loading may not be seen by
        // the view just after setting it.
        marvelLiveData.value = Loading()

        disposable = getMarvelHeroesUseCase.execute(GetMarvelHeroesParams(forceRemote, offset, pageSize))
            .subscribeWith(
                object : SingleRemoteInterceptor<List<MarvelHero>>() {
                    override fun onSuccess(t: List<MarvelHero>) {
                        if (offset > 0) this@MarvelViewModel.marvelHeroes.addAll(t as MutableList)
                        else this@MarvelViewModel.marvelHeroes = t as MutableList
                        marvelLiveData.value = Success(this@MarvelViewModel.marvelHeroes.subList(offset, marvelHeroes.size))
                        offset = marvelHeroes.size
                    }

                    override fun onRegularError(e: Throwable) {
                        marvelLiveData.value = Error(errorBundleBuilder.build(e, GET_MARVEL_HEROES))
                    }
                }
            )
    }
}