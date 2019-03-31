package jmapps.lessonsoframadan.presentation.mvp.model

import android.content.SharedPreferences
import android.view.ViewTreeObserver
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import jmapps.lessonsoframadan.presentation.mvp.presenter.ScrollCountPresenter

class ScrollCountModel(private var sectionNumber: Int,
                       private val mainScroll: NestedScrollView,
                       private val progressScroll: ProgressBar) : ScrollCountPresenter,
        ViewTreeObserver.OnScrollChangedListener {

    override fun scrollCount() {
        val observer: ViewTreeObserver = mainScroll.viewTreeObserver
        observer.addOnScrollChangedListener(this)
    }

    override fun onScrollChanged() {
        progressScroll.max = mainScroll.getChildAt(0).height - mainScroll.height
        progressScroll.progress = mainScroll.scrollY
    }

    override fun saveLastCount(editor: SharedPreferences.Editor) {
        editor.putInt("$sectionNumber=scrollY", mainScroll.scrollY).apply()
        editor.putInt("$sectionNumber=scrollX", mainScroll.scrollX).apply()
    }

    override fun loadLastCount(preferences: SharedPreferences) {
        mainScroll.post {
            val scrollX = preferences.getInt("$sectionNumber=scrollX", 0)
            val scrollY = preferences.getInt("$sectionNumber=scrollY", 0)
            mainScroll.scrollTo(scrollX, scrollY)
        }
    }
}