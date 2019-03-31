package jmapps.lessonsoframadan.presentation.mvp.presenter

import android.content.SharedPreferences

interface OtherPresenter {

    fun openDatabase()

    fun closeDatabase()

    fun saveLastPosition(editor: SharedPreferences.Editor)

    fun loadLastPosition(preferences: SharedPreferences)

    fun setNightMode(editor: SharedPreferences.Editor, state: Boolean)

    fun isNightMode(state: Boolean)

    fun showChapterList()

    fun showFavoriteList()

    fun showSettings()

    fun aboutUs()

    fun rateApp()

    fun shareLink()
}