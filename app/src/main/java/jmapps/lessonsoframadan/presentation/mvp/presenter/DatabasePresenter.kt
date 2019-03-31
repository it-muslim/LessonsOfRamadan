package jmapps.lessonsoframadan.presentation.mvp.presenter

import android.content.SharedPreferences
import android.text.SpannableStringBuilder

interface DatabasePresenter {

    fun getContentFromDatabase(sectionNumber: Int)

    fun stringBuilder(str: String): SpannableStringBuilder

    fun addRemoveFavorite(editor: SharedPreferences.Editor, isChecked: Boolean)

    fun sharedContent()
}