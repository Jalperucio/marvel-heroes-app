package com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hiberus.mobile.android.openbanktest.app.marvel.master.MarvelListNavigationCommand
import com.hiberus.mobile.android.openbanktest.app.common.viewmodel.EventWrapper

class MarvelNavigationViewModel : ViewModel() {

    private val marvelNavigationLiveEvent = MutableLiveData<EventWrapper<MarvelListNavigationCommand>>()

    fun getMarvelNavigationLiveEvents(): LiveData<EventWrapper<MarvelListNavigationCommand>> {
        return marvelNavigationLiveEvent
    }

    fun send(marvelListNavigationCommand: MarvelListNavigationCommand) {
        marvelNavigationLiveEvent.value = EventWrapper(marvelListNavigationCommand)
    }
}