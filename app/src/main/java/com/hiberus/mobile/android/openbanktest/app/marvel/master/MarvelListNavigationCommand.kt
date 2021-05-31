package com.hiberus.mobile.android.openbanktest.app.marvel.master

sealed class MarvelListNavigationCommand {

    object GoToDetailsView : MarvelListNavigationCommand()
}