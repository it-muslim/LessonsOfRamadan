package jmapps.lessonsoframadan.presentation.mvp.presenter

import android.content.SharedPreferences

interface ScrollCountPresenter {

    fun scrollCount()

    fun saveLastCount(editor: SharedPreferences.Editor)

    fun loadLastCount(preferences: SharedPreferences)
}