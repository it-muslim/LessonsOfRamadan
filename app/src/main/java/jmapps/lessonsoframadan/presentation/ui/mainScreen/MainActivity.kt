package jmapps.lessonsoframadan.presentation.ui.mainScreen

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import jmapps.lessonsoframadan.R
import jmapps.lessonsoframadan.presentation.mvp.model.OtherModel
import jmapps.lessonsoframadan.presentation.mvp.view.OtherView
import jmapps.lessonsoframadan.presentation.ui.chapterList.ChapterList
import jmapps.lessonsoframadan.presentation.ui.favoriteList.FavoriteList
import jmapps.lessonsoframadan.presentation.ui.mainContainer.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        ChapterList.SetCurrentPageChapter, FavoriteList.SetCurrentPageFavorite, OtherView {

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var otherPresenter: OtherModel
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var nightModeItem: MenuItem? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        otherPresenter = OtherModel(this, this, supportFragmentManager, mainContainer)
        otherPresenter.openDatabase()
        otherPresenter.isNightMode(preferences.getBoolean("key_night_mode", false))

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        mainContainer.adapter = mSectionsPagerAdapter

        fabChapters.setOnClickListener {
            otherPresenter.showChapterList()
        }

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset < 0) {
                fabChapters.hide()
            } else {
                fabChapters.show()
            }
        })

        otherPresenter.loadLastPosition(preferences)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        nightModeItem = menu.findItem(R.id.action_night_mode)
        nightModeItem!!.isChecked = preferences.getBoolean("key_night_mode", false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_night_mode -> otherPresenter.setNightMode(editor, !nightModeItem!!.isChecked)

            R.id.action_favorites -> otherPresenter.showFavoriteList()

            R.id.action_settings -> otherPresenter.showSettings()

            R.id.action_about_us -> otherPresenter.aboutUs()

            R.id.action_rate -> otherPresenter.rateApp()

            R.id.action_share -> otherPresenter.shareLink()

            R.id.action_exit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun recreateActivity() {
        recreate()
    }

    override fun setPage(currentPosition: Int) {
        mainContainer.currentItem = currentPosition - 1
    }

    override fun onPause() {
        super.onPause()
        otherPresenter.saveLastPosition(editor)
    }

    override fun onStop() {
        super.onStop()
        otherPresenter.saveLastPosition(editor)
    }

    override fun onDestroy() {
        super.onDestroy()
        otherPresenter.closeDatabase()
    }
}