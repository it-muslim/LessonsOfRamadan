package jmapps.lessonsoframadan.presentation.mvp.model

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.util.Linkify
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import jmapps.lessonsoframadan.R
import jmapps.lessonsoframadan.data.database.DatabaseOpenHelper
import jmapps.lessonsoframadan.presentation.mvp.presenter.OtherPresenter
import jmapps.lessonsoframadan.presentation.mvp.view.OtherView
import jmapps.lessonsoframadan.presentation.ui.chapterList.ChapterList
import jmapps.lessonsoframadan.presentation.ui.favoriteList.FavoriteList
import jmapps.lessonsoframadan.presentation.ui.settings.Settings

class OtherModel(
    private val context: Context,
    private val otherView: OtherView,
    private val supportFragmentManager: FragmentManager,
    private val container: ViewPager
) : OtherPresenter {

    override fun openDatabase() {
        val database = DatabaseOpenHelper(context)
        database.writableDatabase
    }

    override fun closeDatabase() {
        val database = DatabaseOpenHelper(context)
        database.close()
    }

    override fun saveLastPosition(editor: SharedPreferences.Editor) {
        editor.putInt("key_last_view_pager_position", container.currentItem).apply()
    }

    override fun loadLastPosition(preferences: SharedPreferences) {
        container.currentItem = preferences.getInt("key_last_view_pager_position", 0)
    }

    override fun setNightMode(editor: SharedPreferences.Editor, state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        editor.putBoolean("key_night_mode", state).apply()
        otherView.recreateActivity()
    }

    override fun isNightMode(state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun showChapterList() {
        ChapterList().show(supportFragmentManager, "chapter_list")
    }

    override fun showFavoriteList() {
        FavoriteList().show(supportFragmentManager, "favorite_list")
    }

    override fun showSettings() {
        Settings().show(supportFragmentManager, "settings")
    }

    override fun aboutUs() {
        AlertDialog.Builder(context).also {

            val tvAboutUs = TextView(context)
            tvAboutUs.autoLinkMask = Linkify.ALL
            tvAboutUs.text = context.getString(R.string.about_us_text)
            tvAboutUs.textSize = 18f
            tvAboutUs.setPadding(32, 32, 32, 32)

            it.setIcon(R.drawable.ic_about_us)
            it.setTitle(R.string.action_about_us)
            it.setView(tvAboutUs)
            it.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            it.create().show()
        }
    }

    override fun rateApp() {
        val link = "https://play.google.com/store/apps/details?id=jmapps.lessonsoframadan"
        val rate = Intent(Intent.ACTION_VIEW)
        rate.data = link.toUri()
        context.startActivity(rate)
    }

    override fun shareLink() {
        val linkDescription = "Советую скачать приложение:\nУроки Рамадана - пост и его значение\n\n"
        val link = "https://play.google.com/store/apps/details?id=jmapps.lessonsoframadan"
        val shareLink = Intent(Intent.ACTION_SEND)
        shareLink.type = "text/plain"
        shareLink.putExtra(Intent.EXTRA_TEXT, "$linkDescription$link")
        context.startActivity(shareLink)
    }
}