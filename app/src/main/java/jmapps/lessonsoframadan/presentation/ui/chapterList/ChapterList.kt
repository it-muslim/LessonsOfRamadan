package jmapps.lessonsoframadan.presentation.ui.chapterList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.lessonsoframadan.R
import jmapps.lessonsoframadan.presentation.mvp.model.DatabaseChapters
import jmapps.lessonsoframadan.presentation.ui.chapterList.adapter.ChapterListAdapter
import jmapps.lessonsoframadan.presentation.ui.chapterList.model.ChapterListModel
import kotlinx.android.synthetic.main.dialog_fragment_chapter_list.view.*

class ChapterList : DialogFragment(), ChapterListAdapter.OnChapterItemClick {

    private lateinit var setCurrentPage: SetCurrentPageChapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootChapterList = inflater.inflate(R.layout.dialog_fragment_chapter_list, container, false)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val vertical = LinearLayoutManager(context)
        rootChapterList.rvChapterList.layoutManager = vertical
        rootChapterList.rvChapterList.hasFixedSize()
        val chapterListModel = DatabaseChapters(context).getChapterList
        val chapterAdapter = ChapterListAdapter(context, chapterListModel, this)
        rootChapterList.rvChapterList.adapter = chapterAdapter

        return rootChapterList
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SetCurrentPageChapter) {
            setCurrentPage = context
        } else {
            throw RuntimeException("$context must implement SetCurrentPageChapter")
        }
    }

    interface SetCurrentPageChapter {
        fun setPage(currentPosition: Int)
    }

    override fun onChapterClick(chapterModel: List<ChapterListModel>, position: Int) {
        val strCurrentId = chapterModel[position].strPositionId
        setCurrentPage.setPage(strCurrentId!!.toInt())
        dialog?.dismiss()
    }
}